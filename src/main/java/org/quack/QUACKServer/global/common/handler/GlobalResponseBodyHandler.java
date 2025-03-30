package org.quack.QUACKServer.global.common.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.quack.QUACKServer.demo.exception.common.BaseCustomException;
import org.quack.QUACKServer.global.common.constans.QuackCode;
import org.quack.QUACKServer.global.common.dto.CommonExceptionResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * @packageName : org.quack.QUACKServer.global.common.handler
 * @fileName : GlobalResponseBodyHandler
 * @date : 25. 3. 29.
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

        // 나중에 공통 Response Body로 path, timestamp 같이 보낼 예정
        String path = request.getURI().getPath();

        switch (body) {
            case QuackCode.DefaultCode detailCode -> {
                servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

                return ResponseEntity
                        .status(detailCode.getHttpStatus()).body(detailCode.getDescription());
            }
            case CommonExceptionResponse commonExceptionResponse -> {
                servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return ResponseEntity
                        .status(commonExceptionResponse.quackCode().getHttpStatus())
                        .body(commonExceptionResponse.customMessage());
            }
            case ResponseEntity<?> responseEntity -> {
                Object actualBody = responseEntity.getBody();
                HttpStatus status = (HttpStatus) responseEntity.getStatusCode();


                servletResponse.setStatus(status.value());

                return actualBody;
            }
            case null, default -> {
                QuackCode.DefaultCode success = QuackCode.DefaultCode.DefaultCode.SUCCESS;
                servletResponse.setStatus(success.getHttpStatus().value());
                return body;
            }
        }

    }

}
