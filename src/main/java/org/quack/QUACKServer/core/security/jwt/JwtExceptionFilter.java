package org.quack.QUACKServer.core.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.jwt
 * @fileName : JwtExceptionFilter
 * @date : 25. 4. 25.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT 토큰 인증 중 Exception 발생, {}", e.getMessage());

            switch (e) {
                case SecurityException securityException -> buildResponse(response, ErrorCode.ACCESS_DENIED);
                case AccessDeniedException accessDeniedException -> buildResponse(response, ErrorCode.ACCESS_DENIED);
                case ExpiredJwtException expiredJwtException -> {
                    if (request.getPathInfo().startsWith("/api/v1/auth/login")) {
                        buildResponse(response, ErrorCode.INVALID_ID_TOKEN);
                    } else {
                        buildResponse(response, ErrorCode.EXPIRED_ACCESS_TOKEN);
                    }
                }
                case IllegalArgumentException illegalArgumentException ->
                        buildResponse(response, ErrorCode.INVALID_REQUEST);
                default -> buildResponse(response, ErrorCode.UNAUTHORIZED_USER);
            }

        }
    }

    private void buildResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(createResponseBody(errorCode));
    }

    private String createResponseBody(ErrorCode errorCode) throws JsonProcessingException {
        ResponseDto<?> response = ResponseDto.fail(errorCode);
        return objectMapper.writeValueAsString(response);
    }
}
