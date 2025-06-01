package org.quack.QUACKServer.core.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.jwt
 * @fileName : JwtTokenFilter
 * @date : 25. 4. 20.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = jwtUtil.getTokenByRequest(request);

        // 토큰이 잘 들어 왔는지 검증
        if (!StringUtils.isEmpty(token) && jwtUtil.isValidToken(token)) {
            CustomerUserInfo customerUserInfo = jwtUtil.getQuackUserByToken(token);

            UsernamePasswordAuthenticationToken beforeAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(customerUserInfo, null, customerUserInfo.getAuthorities());

            // 인증 객체 넣기
            SecurityContextHolder.getContext().setAuthentication(beforeAuthenticationToken);

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String[] excludePath = {"/api/v1/auth", "/api/v1/public"};

        return (
                Arrays.stream(excludePath).anyMatch(path::startsWith)
                        || (HttpMethod.GET.matches(method) && StringUtils.isEmpty(token))
        );

    }
}
