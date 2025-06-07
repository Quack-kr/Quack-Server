package org.quack.QUACKServer.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quack.QUACKServer.core.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json;charset=UTF-8");

        CommonExceptionResponse exceptionResponse = new CommonExceptionResponse(
                ErrorCode.UNAUTHORIZED_USER,
                "로그인이 필요한 요청입니다."
        );

        ResponseDto<?> body = ResponseDto.fail(exceptionResponse);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
