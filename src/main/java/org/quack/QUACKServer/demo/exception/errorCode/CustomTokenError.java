package org.quack.QUACKServer.demo.exception.errorCode;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomTokenError implements ErrorCode {

    REFRESH_TOKEN_NOT_FOUND("token-001", "Refresh Token을 찾을 수 없습니다.", BAD_REQUEST),
    REFRESH_TOKEN_INVALID("token-002", "Refresh Token이 유효하지 않습니다.", BAD_REQUEST);


    private final String code;
    private final String description;
    private final HttpStatus statusCode;
}
