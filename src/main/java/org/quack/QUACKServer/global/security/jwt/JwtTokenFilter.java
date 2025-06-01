package org.quack.QUACKServer.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenValue;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.global.external.redis.repository.QuackAuthTokenManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    private final JwtProvider jwtProvider;
    protected final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private final QuackAuthTokenManager quackAuthTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String jwt = jwtProvider.resolveToken(request);

        // 토큰이 잘 들어 왔는지 검증
        if (!StringUtils.isEmpty(jwt) && jwtProvider.isTokenValid(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);

            String authKey  = jwtProvider.getAuthKey(jwt);
            QuackAuthTokenValue tokenValue = quackAuthTokenManager.findTokenByKey(authKey);

             if(!tokenValue.accessToken().equals(jwt)) {
                 // 중복 로그인 허용할 것인가..?
             }

             updateToken(tokenValue);

            // 레디스 토큰 만료시간 조금 더 두기
            QuackUser quackUser = (QuackUser) jwtProvider.extractUserDetails(jwt);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    quackUser, null, authentication.getAuthorities());

            setDetails(request, token);
            // 인증 객체 넣기
            SecurityContextHolder.getContext().setAuthentication(token);

        }

        filterChain.doFilter(request, response);
    }

    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

    }

    private void updateToken(QuackAuthTokenValue tokenValue) {

        if (tokenValue.isAfterMinute()) {
            QuackAuthTokenValue newTokenValue = QuackAuthTokenValue.updateRequestTime(tokenValue);
            quackAuthTokenManager.insertToken(newTokenValue);
        }
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
