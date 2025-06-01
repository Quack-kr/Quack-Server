package org.quack.QUACKServer.global.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : CommonResponse
 * @date : 25. 4. 14.
 */
@Builder(access = AccessLevel.PRIVATE)
public record CommonResponse(
        String code,
        String message,
        HttpStatusCode httpStatusCode,
        Object data
) {
    public static CommonResponse of(String code, String message, HttpStatusCode httpStatusCode, Object data) {
        return CommonResponse.builder()
                .code(code)
                .message(message)
                .httpStatusCode(httpStatusCode)
                .data(data)
                .build();
    }


    public static CommonResponse success(HttpStatus httpStatus, String message, Object data) {

        return CommonResponse.builder()
                .code(String.valueOf(httpStatus.value()))
                .message(message)
                .data(data)
                .httpStatusCode(httpStatus)
                .build();
    }
}