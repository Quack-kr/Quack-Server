package org.quack.QUACKServer.core.error.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.CommonExceptionResponse;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseDto<?> handleCommonException(CommonException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("[CommonException] {}", errorCode.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(errorCode, errorCode.getMessage()));
    }

    // Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<?> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ErrorCode.INVALID_REQUEST.getMessage());

        log.warn("[Validation] {}", message);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.INVALID_REQUEST, message));
    }

    // @Validated bind failed
    @ExceptionHandler(BindException.class)
    public ResponseDto<?> handleBindException(BindException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ErrorCode.INVALID_REQUEST.getMessage());

        log.warn("[BindException] {}", message);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.INVALID_REQUEST, message));
    }

    // @Validated params failed
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseDto<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .findFirst()
                .orElse(ErrorCode.INVALID_REQUEST.getMessage());

        log.warn("[ConstraintViolation] {}", message);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.INVALID_REQUEST, message));
    }

    // JSON parser failed
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDto<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("[JsonParseError] {}", ex.getMessage());
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.INVALID_REQUEST, "요청 형식을 확인해주세요."));
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseDto<?> handleException(Exception ex) {
        log.error("[UnhandledException]", ex);
        return ResponseDto.fail(new CommonExceptionResponse(ErrorCode.SERVER_ERROR, "서버 오류가 발생했습니다."));
    }
}
