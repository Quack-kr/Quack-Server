package org.quack.QUACKServer.core.common;

import jakarta.servlet.http.HttpServletResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.error.handler
 * @fileName : ResponseBodyHandler
 * @date : 25. 6. 1.
 */
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        switch (body) {

            case CommonException commonException -> {
                servletResponse.setStatus(commonException.getErrorCode().getHttpStatus().value());
                return ResponseDto.builder()
                        .data(null)
                        .message(commonException.getMessage())
                        .httpStatus(commonException.getErrorCode().getHttpStatus())
                        .build();
            }
            case ResponseDto<?> responseDto -> {
                servletResponse.setStatus(responseDto.httpStatus().value());
                return responseDto;
            }
            default -> {
                servletResponse.setStatus(HttpStatus.OK.value());
                return ResponseDto.success(body);
            }
        }
    }
}
