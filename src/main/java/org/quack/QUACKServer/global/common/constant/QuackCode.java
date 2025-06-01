package org.quack.QUACKServer.global.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public interface QuackCode {
    HttpStatus getHttpStatus();

    String getDescription();

    String getCode();

    @Getter
    enum DefaultCode implements QuackCode {
        SUCCESS(HttpStatus.OK, "성공"),
        FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "실패");

        final HttpStatus httpStatus;
        final String description;

        DefaultCode(HttpStatus httpStatus, String description) {
            this.httpStatus = httpStatus;
            this.description = description;
        }

        @Override
        public String getCode() {
            return name();
        }
    }

    @Getter
    enum ExceptionCode implements QuackCode {

        // Error 별 응답 코드 작성
        INVALID_REQUEST_INFO(BAD_REQUEST, "요청값이 잘못되었습니다."),
        MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),

        /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
        INVALID_REFRESH_TOKEN(UNAUTHORIZED, "인증되지 않은 접근입니다"),
        UN_AUTHENTICATION_ACCESS(UNAUTHORIZED, "인증되지 않은 접근입니다"),
        LOGIN_FAIL(UNAUTHORIZED, "로그인에 실패하였습니다"),
        FORBIDDEN_ACCESS(FORBIDDEN, "권한이 없는 접근입니다."),

        // 500 ERROR
        SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류"),

        // 닉네임
        USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
        CURRENT_USE_NICKNAME(FORBIDDEN, "현재 설정된 닉네임입니다."),
        DUPLICATE_NICKNAME(FORBIDDEN, "이미 사용중인 닉네임입니다."),
        INVALID_NULL_NICKNAME(BAD_REQUEST, "닉네임은 null일 수 없습니다."),
        INVALID_BLANK_NICKNAME(BAD_REQUEST, "닉네임은 blank일 수 없습니다."),
        INVALID_LENGTH_NICKNAME(BAD_REQUEST, "닉네임은 2자 이상 15자 미만으로 입력해야 합니다."),
        INVALID_PATTERN_NICKNAME(BAD_REQUEST, "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다."),

        ;
        final HttpStatus httpStatus;
        final String description;

        ExceptionCode(HttpStatus httpStatus, String description) {
            this.httpStatus = httpStatus;
            this.description = description;
        }

        public String getCode(){
            return name();
        }

    }


}
