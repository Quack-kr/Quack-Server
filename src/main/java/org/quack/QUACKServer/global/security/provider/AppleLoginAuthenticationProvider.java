package org.quack.QUACKServer.global.security.provider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.user.domain.User;
import org.quack.QUACKServer.domain.user.service.UserService;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.infra.social.apple.AppleHttpInterface;
import org.quack.QUACKServer.global.infra.social.apple.dto.ApplePublicKeys;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.quack.QUACKServer.global.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

/**
 * @author : jung-kwanhee
 * @description : 실제 인증 객체로 변환 해주는 Provider
 * @packageName : org.quack.QUACKServer.global.config.security.handler
 * @fileName : AppleLoginAuthenticationProvider
 * @date : 25. 4. 15.
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleLoginAuthenticationProvider implements LoginAuthenticationProvider {

    private final AppleHttpInterface appleHttpInterface;
    private final PublicKeyProvider publicKeyProvider;
    private final JwtProvider jwtProvider;
    private final UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        QuackAuthenticationToken quackAuthenticationToken = (QuackAuthenticationToken) authentication;
        ClientType socialType = quackAuthenticationToken.getClientType();

        SocialAuthDto socialAuthDto = getSocialAuth(quackAuthenticationToken.getIdToken());
        QuackUser quackUser = (QuackUser) userService.loadUserByUsername(socialAuthDto.getProviderId());

        if (quackUser.isBeforeSignUp()) {
            if(quackUser.getUserId() == null) {
                User user = userService.createBeforeSignUp(socialType, socialAuthDto);
                quackUser = QuackUser.from(user);
            }
            throw new BeforeSignUpException("회원가입을 해야합니다", quackUser);
        } else {
            return quackAuthenticationToken;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }


    @Override
    public SocialAuthDto getSocialAuth(final String idToken) {
        // 애플에서 공개키를 주면 (idToken -> ~
        ApplePublicKeys applePublicKeys = appleHttpInterface.getApplePublicKeys();
        PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), applePublicKeys);
        Claims claims = jwtProvider.parseClaims(idToken, publicKey);

        return SocialAuthDto.from(claims);
    }
}
