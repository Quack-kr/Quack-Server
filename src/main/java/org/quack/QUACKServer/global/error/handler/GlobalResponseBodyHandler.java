package org.quack.QUACKServer.global.error.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.dto.BaseResponse;
import org.quack.QUACKServer.global.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.core.MethodParameter;
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
 * @fileName : GlobalResponseBodyHandler
 * @date : 25. 6. 1.
 */
@RestControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        String path = request.getURI().getPath();

        switch (body) {
            case QuackCode.ExceptionCode exceptionCode -> {
                servletResponse.setStatus(exceptionCode.getHttpStatus().value());
                return build(path, null, exceptionCode.getDescription(), exceptionCode.name());
            }
            case CommonResponse commonResponse -> {
                servletResponse.setStatus(commonResponse.httpStatusCode().value());
                return build(path, commonResponse.data(), commonResponse.message(),
                        commonResponse.code());
            }
            case CommonExceptionResponse commonExceptionResponse -> {
                servletResponse.setStatus(commonExceptionResponse.exceptionCode().getHttpStatus().value());
                return build(path, null, commonExceptionResponse.customMessaged(), commonExceptionResponse.exceptionCode().getCode());
            }
            default -> {
                QuackCode.ExceptionCode success = QuackCode.ExceptionCode.SUCCESS;
                servletResponse.setStatus(success.getHttpStatus().value());
                return build(path, body, success.getDescription(), success.name());
            }
        }
    }

    private BaseResponse<Object> build(String path, Object data, String message, String code) {
        return BaseResponse.builder()
                .path(path)
                .data(data)
                .message(message)
                .code(code)
                .build();
    }
}
