//package org.quack.QUACKServer.global.security.jwt;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * @author : jung-kwanhee
// * @description :
// * @packageName : org.quack.QUACKServer.global.security.jwt
// * @fileName : JwtTokenFilter
// * @date : 25. 4. 20.
// */
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtProvider jwtProvider;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        final String jwt = jwtProvider.resolveToken(request);
//
//        if(StringUtils.hasText(jwt) && jwtProvider.isTokenValid(jwt)) {
//            // Authentication authentication = jwtProvider.getAuthentication(jwt);
//            //String authKey = jwtProvider.getAuthKey(jwt);
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
