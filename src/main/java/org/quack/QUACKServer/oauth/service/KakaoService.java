package org.quack.QUACKServer.oauth.service;

import static org.quack.QUACKServer.exception.errorCode.CustomKakaoLoginError.INVAILD_KAKAO_ACCESS_TOKEN;
import static org.quack.QUACKServer.exception.errorCode.CustomKakaoLoginError.KAKAO_SERVER_ERROR;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.config.jwt.JwtProvider;
import org.quack.QUACKServer.config.jwt.UserAuthentication;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.domain.common.SocialType;
import org.quack.QUACKServer.dto.auth.AuthResponse;
import org.quack.QUACKServer.exception.exception.CustomKakaoLoginException;
import org.quack.QUACKServer.oauth.dto.KakaoUserInfo;
import org.quack.QUACKServer.repository.UserRepository;
import org.quack.QUACKServer.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;

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

        // refresh token - Redis 저장


        boolean isNewUser = user.getNickname().equals("k*");

        return AuthResponse.of(accessToken, refreshToken, isNewUser);
    }
}

/*
 TODO:
 1. 카카오 로그아웃
 2. 카카오 회원탈퇴
 */