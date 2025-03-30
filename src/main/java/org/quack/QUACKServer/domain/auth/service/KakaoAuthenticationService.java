package org.quack.QUACKServer.domain.auth.service;

import static org.quack.QUACKServer.demo.exception.errorCode.CustomKakaoLoginError.INVAILD_KAKAO_ACCESS_TOKEN;
import static org.quack.QUACKServer.demo.exception.errorCode.CustomKakaoLoginError.KAKAO_SERVER_ERROR;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.global.jwt.JwtProvider;
import org.quack.QUACKServer.demo.domain.User;
import org.quack.QUACKServer.demo.domain.common.SocialType;
import org.quack.QUACKServer.demo.dto.auth.AuthResponse;
import org.quack.QUACKServer.demo.oauth.dto.KakaoUserInfo;
import org.quack.QUACKServer.demo.exception.exception.CustomKakaoLoginException;
import org.quack.QUACKServer.demo.repository.UserRepository;
import org.quack.QUACKServer.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
@Transactional
public class KakaoAuthenticationService implements AuthenticationService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Value("${kakao.user-info-url}")
    private String kakaoUserInfoUrl;

    @Value("${kakao.logout-url}")
    private String kakaoLogoutUrl;

    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    @Value("${kakao.unlink-url}")
    private String kakaoUnlinkUrl;

    @Value("${default.user-profileImage}")
    private String defaultProfileImage;

    @Override
    public AuthResponse loginUser(String kakaoAccessToken) {
        RestClient restCLient = RestClient.create();

        KakaoUserInfo userInfo = restCLient.get()
                .uri(kakaoUserInfoUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomKakaoLoginException(INVAILD_KAKAO_ACCESS_TOKEN, "kakao access token is invalid");
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new CustomKakaoLoginException(KAKAO_SERVER_ERROR, "kakao server didn't work for request");
                }))
                .body(KakaoUserInfo.class);

        String defaultNickname = "k*";

        User user = userRepository.findBySocialTypeAndSocialId(SocialType.KAKAO.name(), userInfo.socialId());
        if (user == null) {
            user = userRepository.save(User.createBySocial(
                    SocialType.KAKAO,
                    userInfo.socialId(),
                    userInfo.email(),
                    defaultNickname,
                    defaultProfileImage
            ));
        }


        String accessToken = jwtProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());

        redisService.saveRefreshToken(user.getUserId(), refreshToken);

        boolean isNewUser = user.getNickname().equals("k*");

        return AuthResponse.of(accessToken, refreshToken, isNewUser);
    }

    @Override
    public void logout(User user) {
        request(user.getSocialId(), kakaoLogoutUrl);
    }

    public void kakaoWithdraw(User user) {
        request(user.getSocialId(), kakaoUnlinkUrl);
    }

    @Override
    public void request(Long socialId, String requestUrl) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(socialId));

        RestClient restClient = RestClient.create();

        // TODO : HttpInterface 로 이관 예정
        restClient.post()
                .uri(requestUrl)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoAdminKey)
                .body(params)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CustomKakaoLoginException(KAKAO_SERVER_ERROR, "kakao server didn't work for request");
                })
                .toBodilessEntity();
    }

    @Override
    public User attemptLogin(String token) {
        RestClient restCLient = RestClient.create();

        KakaoUserInfo userInfo = restCLient.get()
                .uri(kakaoUserInfoUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomKakaoLoginException(INVAILD_KAKAO_ACCESS_TOKEN, "kakao access token is invalid");
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new CustomKakaoLoginException(KAKAO_SERVER_ERROR, "kakao server didn't work for request");
                }))
                .body(KakaoUserInfo.class);

        String defaultNickname = "k*";

        User user = userRepository.findBySocialTypeAndSocialId(SocialType.KAKAO.name(), userInfo.socialId());

        if (user == null) {
            user = userRepository.save(User.createBySocial(
                    SocialType.KAKAO,
                    userInfo.socialId(),
                    userInfo.email(),
                    defaultNickname,
                    defaultProfileImage
            ));
        }



        return user;
    }
}

/*
 TODO:
 1. 카카오 회원탈퇴
 */