package org.quack.QUACKServer.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author : changhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.jwt
 * @fileName : JwtFilter
 * @date : 25. 3. 29.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final JwtUtils jwtUtils;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String token = getToken(request);
            final JwtValidationType validationResult = jwtUtils.validateJwt(token);

            if (validationResult == JwtValidationType.ACCESS_VALID) {
                Long userId = jwtUtils.getUserIdFromJwt(token);
                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (validationResult == JwtValidationType.ACCESS_EXPIRED) {
                String code = "\"AUTH-001\"";
                String message = "\"Access token expired. Please use refresh token to reissue.\"";
                setResponse(response, code, message);
                return;
            } else {
                String code = "\"AUTH-002\"";
                String message = "\"Invalid token.\"";
                setResponse(response, code, message);
                return;
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private void setResponse(HttpServletResponse response, String code, String message) throws IOException{
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter()
                .write("{\"code\": "+ code + ", \"message\": " + message + "}");
    }

}
