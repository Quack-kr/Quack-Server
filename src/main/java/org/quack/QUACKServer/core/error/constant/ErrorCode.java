package org.quack.QUACKServer.core.error.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_REQUEST(BAD_REQUEST, "요청값이 잘못되었습니다."),
    INVALID_PROVIDER_TYPE(BAD_REQUEST, "소셜 로그인 인증 타입이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "리프레시 토큰 발급하는데 오류가 발생하였습니다"),
    EXPIRED_ACCESS_TOKEN(UNAUTHORIZED, "엑세소 토큰이 만료되었습니다."),
    INVALID_ID_TOKEN(UNAUTHORIZED, "ID 토큰이 유효하지 않습니다."),

    UNAUTHORIZED_USER(UNAUTHORIZED, "인증되지 않은 접근입니다"),
    ACCESS_DENIED(FORBIDDEN, "액세스 권한이 없습니다."),

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류"),
    PHOTO_UPLOAD_ERROR(INTERNAL_SERVER_ERROR, "이미지를 업로드하는데 오류가 발생하였습니다."),
    PARSING_ERROR(INTERNAL_SERVER_ERROR, "응답값 변환 시 오류 발생하였습니다."),

    USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
    CURRENT_USE_NICKNAME(FORBIDDEN, "현재 설정된 닉네임입니다."),
    DUPLICATE_NICKNAME(FORBIDDEN, "이미 사용중인 닉네임입니다."),
    INVALID_NULL_NICKNAME(BAD_REQUEST, "닉네임은 null일 수 없습니다."),
    INVALID_BLANK_NICKNAME(BAD_REQUEST, "닉네임은 blank일 수 없습니다."),
    INVALID_SHORT_LENGTH_NICKNAME(BAD_REQUEST, "닉네임은 3자 이상 입력해야 합니다."),
    INVALID_LONG_LENGTH_NICKNAME(BAD_REQUEST, "닉네임은 15자 미만 입력해야 합니다."),
    INVALID_PATTERN_NICKNAME(BAD_REQUEST, "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다."),

    ;

    final HttpStatus httpStatus;
    final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
