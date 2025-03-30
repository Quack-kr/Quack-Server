package org.quack.QUACKServer.global.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.demo.domain.User;
import org.quack.QUACKServer.demo.service.RedisService;
import org.quack.QUACKServer.domain.auth.service.AuthenticationService;
import org.quack.QUACKServer.global.common.constans.QuackCode;
import org.quack.QUACKServer.global.common.exception.BaseCustomException;
import org.quack.QUACKServer.global.jwt.JwtProvider;
import org.quack.QUACKServer.global.security.LoginType;
import org.quack.QUACKServer.global.security.SecurityConfig;
import org.quack.QUACKServer.global.security.SessionContextHolder;
import org.quack.QUACKServer.global.security.dto.ClientSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.filter
 * @fileName : SocialLoginProvider
 * @date : 25. 3. 29.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SocialLoginProvider extends DefaultOAuth2UserService {

    @Qualifier("kakaoAuthenticationService")
    private final AuthenticationService kakaoAuthenticationService;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();

        LoginType loginType = LoginType.of(userRequest.getClientRegistration().getRegistrationId());

        switch (loginType) {
            case KAKAO -> {

                User kakaoUser = kakaoAuthenticationService.attemptLogin(accessToken);

                Set<SimpleGrantedAuthority> authorities = Set.of(
                        new SimpleGrantedAuthority(kakaoUser.getRoleType().name()));

//                String accessToken = jwtProvider.createAccessToken(kakaoUser.getUserId());
                String refreshToken = jwtProvider.createRefreshToken(kakaoUser.getUserId());

                redisService.saveRefreshToken(kakaoUser.getUserId(), refreshToken);

                return  ClientSession.builder()
                        .authorities(authorities)
                        .attributes(oAuth2User.getAttributes())
                        .user(kakaoUser)
                        .build();
            }
            case null, default -> {
                throw new BaseCustomException(QuackCode.DetailCode.INVAILD_KAKAO_ACCESS_TOKEN);
            }
        }
    }
}
