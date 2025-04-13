package org.quack.QUACKServer.global.common.dto;

import org.springframework.http.HttpStatusCode;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : CommonResponse
 * @date : 25. 4. 14.
 */
public record CommonResponse(
        String code,
        String message,
        HttpStatusCode httpStatusCode,
        Object data
) {
    public static CommonResponse of(String code, String message, HttpStatusCode httpStatusCode, Object data) {
        return new CommonResponse(code, message, httpStatusCode, data);
    }
}