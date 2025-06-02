package org.quack.QUACKServer.core.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseDto<?> customException(Exception exception) {
        log.error("개발자 에러 발생", exception);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

}
