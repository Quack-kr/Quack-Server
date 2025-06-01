package org.quack.QUACKServer.core.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(createResponseBody());
        }
    }


    private String createResponseBody() {

        try {
            ResponseDto<?> response = ResponseDto.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(ErrorCode.SERVER_ERROR.getMessage())
                    .isSuccess(false)
                    .timestamp(LocalDateTime.now())
                    .build();

            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex) {
            throw new CommonException(ErrorCode.UN_AUTHENTICATION_ACCESS);
        }
    }
}
