package org.quack.QUACKServer.core.error.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseDto<?> customException(Exception exception) {
        log.error("Global Exception", exception);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

    @ExceptionHandler({ AuthenticationException.class})
    public ResponseDto<?> handleAuthenticationException(AuthenticationException exception) {
        log.error("AuthenticationException, {}", exception.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

    @ExceptionHandler({ AccessDeniedException.class})
    public ResponseDto<?> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("AccessDeniedException, {}", exception.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

    @ExceptionHandler({ JwtException.class})
    public ResponseDto<?> handleException(JwtException exception) {
        log.error("JwtException, {}", exception.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseDto<?> handleJwtExpireException(ExpiredJwtException exception) {
        log.error("ExpiredJwtException, {}", exception.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "예외 발생"));
    }

}
