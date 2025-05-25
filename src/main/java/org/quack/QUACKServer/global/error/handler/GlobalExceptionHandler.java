package org.quack.QUACKServer.global.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode;
import org.quack.QUACKServer.global.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.global.error.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonExceptionResponse customException(HttpServletRequest request, Exception exception) {

        QuackCode.ExceptionCode exceptionCode = QuackCode.ExceptionCode.SERVER_ERROR;

        log.error("Global Exception", exception);
        return new CommonExceptionResponse(exceptionCode, exceptionCode.getDescription());
    }

    // CustomException 별 추가
}
