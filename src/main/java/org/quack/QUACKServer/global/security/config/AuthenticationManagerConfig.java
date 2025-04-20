package org.quack.QUACKServer.global.security.config;

import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.quack.QUACKServer.global.security.provider.AppleLoginAuthenticationProvider;
import org.quack.QUACKServer.global.security.provider.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.ArrayList;
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
public class AuthenticationManagerConfig {

    private final List<AuthenticationProvider> authenticationProviders;

    public AuthenticationManagerConfig(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Bean
    public AuthenticationManager quackAuthenticationManager() {
        return new ProviderManager(authenticationProviders);
    }
}
