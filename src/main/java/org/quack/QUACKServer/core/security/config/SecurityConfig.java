package org.quack.QUACKServer.core.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.security.filter.SocialAuthLoginFilter;
import org.quack.QUACKServer.core.security.jwt.JwtExceptionFilter;
import org.quack.QUACKServer.core.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   @Qualifier("handlerMappingIntrospector") HandlerMappingIntrospector introspector,
                                                   SocialAuthLoginFilter socialAuthLoginFilter,
                                                   JwtTokenFilter jwtTokenFilter,
                                                   JwtExceptionFilter jwtExceptionFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(quackAuthenticationManager)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher(introspector, "/v1/public/**")).hasAuthority("ANONYMOUS")
                        .requestMatchers(new MvcRequestMatcher(introspector, "/v1/auth/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/v1/common/health-check")).permitAll()
                        .anyRequest().authenticated()
                )
                .anonymous(anon -> anon
                        .principal("guest-user")
                        .authorities("ANONYMOUS")
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(socialAuthLoginFilter, LogoutFilter.class)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtTokenFilter.class)
                .logout(logout -> logout
                        .logoutSuccessUrl("/v1/auth/logout")
                        .logoutSuccessHandler((request, response, auth) -> SecurityContextHolder.clearContext())
                )
                .build();
    }

    @Bean
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
