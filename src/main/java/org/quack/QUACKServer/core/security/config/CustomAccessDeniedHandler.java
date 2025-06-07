package org.quack.QUACKServer.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quack.QUACKServer.core.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404로 설정
        response.setContentType("application/json;charset=UTF-8");

        CommonExceptionResponse exceptionResponse = new CommonExceptionResponse(
                ErrorCode.ACCESS_DENIED,
                "접근이 거부되었습니다. 리소스를 찾을 수 없습니다."
        );

        ResponseDto<?> body = ResponseDto.fail(exceptionResponse);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
