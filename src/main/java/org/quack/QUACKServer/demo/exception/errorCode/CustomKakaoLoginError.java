package org.quack.QUACKServer.demo.exception.errorCode;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomKakaoLoginError implements ErrorCode {

    INVAILD_KAKAO_ACCESS_TOKEN("KAKAO-001", "Kakao Access token이 유효하지 않습니다.", UNAUTHORIZED),
    KAKAO_SERVER_ERROR("KAKAO-002", "Kakao Server Error", SERVICE_UNAVAILABLE);



    private final String code;
    private final String description;
    private final HttpStatus statusCode;

}
