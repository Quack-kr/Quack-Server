package org.quack.QUACKServer.global.error.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.dto.CommonExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.error.handler
 * @fileName : AuthenticationExceptionHandler
 * @date : 25. 6. 1.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class, AccessDeniedException.class, JwtException.class})
    public CommonExceptionResponse handleException(HttpServletRequest request, Exception ex) {
        log.error("AuthenticationExceptionHandler.handleException", ex);
        QuackCode.ExceptionCode exceptionCode = getExceptionCode(ex);
        return new CommonExceptionResponse(exceptionCode, null);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public CommonExceptionResponse handleJwtExpireException(HttpServletRequest request, Exception ex) {
        log.error("AuthenticationExceptionHandler.handleException", ex);
        QuackCode.ExceptionCode exceptionCode = getExceptionCode(ex);
        return new CommonExceptionResponse(exceptionCode, null);
    }

    private QuackCode.ExceptionCode getExceptionCode(Exception ex) {
        return switch (ex) {
            case AccessDeniedException accessDeniedException -> QuackCode.ExceptionCode.FORBIDDEN_ACCESS;
            case AuthenticationException authenticationException -> QuackCode.ExceptionCode.UN_AUTHENTICATION_ACCESS;
            case JwtException expiredJwtException -> QuackCode.ExceptionCode.UN_AUTHENTICATION_ACCESS;
            case null, default -> QuackCode.ExceptionCode.SERVER_ERROR;
        };
    }
}
