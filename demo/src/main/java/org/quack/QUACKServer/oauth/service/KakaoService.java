package org.quack.QUACKServer.oauth.service;

import static org.quack.QUACKServer.exception.errorCode.CustomKakaoLoginError.INVAILD_KAKAO_ACCESS_TOKEN;
import static org.quack.QUACKServer.exception.errorCode.CustomKakaoLoginError.KAKAO_SERVER_ERROR;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.config.jwt.JwtProvider;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.domain.common.SocialType;
import org.quack.QUACKServer.dto.auth.AuthResponse;
import org.quack.QUACKServer.exception.exception.CustomKakaoLoginException;
import org.quack.QUACKServer.oauth.dto.KakaoUserInfo;
import org.quack.QUACKServer.repository.UserRepository;
import org.quack.QUACKServer.service.RedisService;
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
public class KakaoService {

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

    public AuthResponse getKakaoUserInfo(String kakaoAccessToken) {
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

    public void kakaoLogout(User user) {
        kakaoRequest(user.getSocialId(), kakaoLogoutUrl);
    }

    public void kakaoWithdraw(User user) {
        kakaoRequest(user.getSocialId(), kakaoUnlinkUrl);
    }

    private void kakaoRequest(Long socialId, String requestUrl) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(socialId));

        RestClient restClient = RestClient.create();

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
}

/*
 TODO:
 1. 카카오 회원탈퇴
 */