package org.quack.QUACKServer.global.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.security.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author : jung-kwanhee
 * @description : 
 * @packageName : org.quack.QUACKServer.global.config.security
 * @fileName : SecurityConfig
 * @date : 25. 4. 14.
 */

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final AuthenticationManager quackAuthenticationManager;
    private final LoginFilter loginFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(quackAuthenticationManager)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/v1/public/*","/auth/*","common/health-check").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
