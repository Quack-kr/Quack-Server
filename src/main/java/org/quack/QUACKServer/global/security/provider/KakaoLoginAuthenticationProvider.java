package org.quack.QUACKServer.global.security.provider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthenticationToken;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.service.UserService;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.infra.social.kakao.KakaoHttpInterface;
import org.quack.QUACKServer.global.infra.social.kakao.dto.KakaoPublicKeys;
import org.quack.QUACKServer.global.security.enums.ProviderType;
import org.quack.QUACKServer.global.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.provider
 * @fileName : KakaoLoginAuthenticationProvider
 * @date : 25. 5. 31.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginAuthenticationProvider implements LoginAuthenticationProvider{

    private final KakaoHttpInterface kakaoHttpInterface;
    private final PublicKeyProvider publicKeyProvider;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Override
    public SocialAuthDto getSocialAuth(String idToken) {
        KakaoPublicKeys kakaoPublicKeys = kakaoHttpInterface.getKakaoPublicKey();
        PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), kakaoPublicKeys);
        Claims claims = jwtProvider.parseClaims(idToken, publicKey);
        return SocialAuthDto.from(claims);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        QuackAuthenticationToken quackAuthenticationToken = (QuackAuthenticationToken) authentication;
        ProviderType provider = quackAuthenticationToken.getProvider();

        SocialAuthDto socialAuthDto = getSocialAuth(quackAuthenticationToken.getIdToken());
        QuackUser quackUser = userService.loadUserByUsername(socialAuthDto.getProviderId());

        if(quackUser.isEmpty()) {
            throw new BeforeSignUpException("회원가입을 해야합니다", quackUser.toBuilder().email(socialAuthDto.getEmail()).build());
        }
        return new QuackAuthenticationToken(quackUser, provider, quackAuthenticationToken.getAccessToken(),quackAuthenticationToken.getIdToken());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
