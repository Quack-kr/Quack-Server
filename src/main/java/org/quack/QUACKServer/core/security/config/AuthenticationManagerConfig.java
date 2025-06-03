package org.quack.QUACKServer.core.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.config
 * @fileName : AuthenticationManagerConfig
 * @date : 25. 4. 15.
 */

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AuthenticationManagerConfig implements AuthenticationManager{

    private final List<AuthenticationProvider> authenticationProviders;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for(AuthenticationProvider provider : authenticationProviders) {
            if(provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }

        throw new ProviderNotFoundException("해당 소셜 Provider를 찾을 수 없습니다." + authentication.getClass());
    }
}
