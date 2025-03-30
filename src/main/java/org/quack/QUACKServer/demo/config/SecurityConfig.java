//package org.quack.QUACKServer.demo.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
///**
// * 여기서는 간단히 소셜 로그인 API는 permitAll, 나머지는 인증 필요로 설정
// */
//@Configuration
//@RequiredArgsConstructor
//@Deprecated
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        // 카카오 소셜 로그인 엔드포인트 등은 인증 없이 접근 허용
//                        .requestMatchers("/api/v1/auth/kakao/**", "/api/v1/auth/check-nickname").permitAll()
//                        // 그 외의 API 요청은 인증 필요
//                        .anyRequest().authenticated()
//                )
//                // 폼 로그인이나 세션 로그인 사용 안 할 경우 disable
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }
//}