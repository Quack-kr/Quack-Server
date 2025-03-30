package org.quack.QUACKServer.global.security;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.auth.service.AuthenticationService;
import org.quack.QUACKServer.global.jwt.JwtFilter;
import org.quack.QUACKServer.global.security.filter.ClientAuthenticationFilter;
import org.quack.QUACKServer.global.security.filter.RequestParamCamelizingFilter;
import org.quack.QUACKServer.global.security.filter.SocialLoginProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security
 * @fileName : SecurityConfig
 * @date : 25. 3. 29.
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final RequestParamCamelizingFilter requestParamCamelizingFilter;
    private final JwtFilter jwtFilter;
    private final SocialLoginProvider socialLoginProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 인증처리 및 비로그인 처리 시 사용 가능한 API 접근 허용
                        .requestMatchers("/api/v1/auth/**", "/api/v1/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Json Convert 관련 필터
                .addFilterBefore(requestParamCamelizingFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(socialLoginProvider)))
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // jwt 인증 실패 시 필터
//                .addFilterBefore(jwtExceptionFilter, JwtExceptionFilter.class)
//                .logout(httpSecurityLogoutConfigurer -> )
                .build();

    }




}
