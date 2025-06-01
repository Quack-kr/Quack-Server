package org.quack.QUACKServer.core.security.provider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.QuackAuthenticationDto;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.auth.service.UserService;
import org.quack.QUACKServer.core.common.dto.SocialAuthDto;
import org.quack.QUACKServer.core.external.social.apple.AppleHttpInterface;
import org.quack.QUACKServer.core.external.social.apple.dto.ApplePublicKeys;
import org.quack.QUACKServer.core.security.enums.ProviderType;
import org.quack.QUACKServer.core.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.core.security.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        QuackAuthenticationDto quackAuthenticationDto = (QuackAuthenticationDto) authentication;
        ProviderType provider = quackAuthenticationDto.getProvider();

        SocialAuthDto socialAuthDto = getSocialAuth(quackAuthenticationDto.getIdToken());
        CustomerUserInfo customerUserInfo = userService.loadUserByUsername(socialAuthDto.getProviderId());

        if(customerUserInfo.isEmpty()) {
            throw new BeforeSignUpException("회원가입을 해야합니다", customerUserInfo.toBuilder().email(socialAuthDto.getEmail()).build());
        }
        return new QuackAuthenticationDto(customerUserInfo, provider, quackAuthenticationDto.getAccessToken(), quackAuthenticationDto.getIdToken());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }


    @Override
    public SocialAuthDto getSocialAuth(final String idToken) {
        ApplePublicKeys applePublicKeys = appleHttpInterface.getApplePublicKeys();
        PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), applePublicKeys);
        Claims claims = jwtUtil.getClaimsByPublicKey(idToken, publicKey);

        return SocialAuthDto.from(claims);
    }
}
