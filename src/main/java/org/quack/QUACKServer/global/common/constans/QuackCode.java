package org.quack.QUACKServer.global.common.constans;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.constans
 * @fileName : QuackCode
 * @date : 25. 3. 29.
 */
public interface QuackCode {
    HttpStatusCode getHttpStatus();

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
    enum DetailCode implements QuackCode {

        /** 카카오 로그인 에러 코드 **/
        INVAILD_KAKAO_ACCESS_TOKEN("KAKAO-001", "Kakao Access token이 유효하지 않습니다.", UNAUTHORIZED),
        KAKAO_SERVER_ERROR("KAKAO-002", "Kakao Server Error", SERVICE_UNAVAILABLE),

        /** 토큰 에러 코드 **/
        REFRESH_TOKEN_NOT_FOUND("token-001", "Refresh Token을 찾을 수 없습니다.", BAD_REQUEST),
        REFRESH_TOKEN_INVALID("token-002", "Refresh Token이 유효하지 않습니다.", BAD_REQUEST),

        /** 유저 관련 에러 코드 **/
        USER_NOT_FOUND("user-001", "유저를 찾을 수 없습니다.", BAD_REQUEST),
        CURRENT_USE_NICKNAME("user-002", "현재 설정된 닉네임입니다.", BAD_REQUEST),
        DUPLICATE_NICKNAME("user-003", "이미 사용중인 닉네임입니다.", CONFLICT),
        INVALID_NULL_NICKNAME("user-004", "닉네임은 null일 수 없습니다.", BAD_REQUEST),
        INVALID_BLANK_NICKNAME("user-005", "닉네임은 blank일 수 없습니다.", BAD_REQUEST),
        INVALID_LENGTH_NICKNAME("user-006", "닉네임은 2자 이상 15자 미만으로 입력해야 합니다.", BAD_REQUEST),
        INVALID_PATTERN_NICKNAME("user-007", "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다.", BAD_REQUEST),


        ;

        final String code;
        final String description;
        final HttpStatus statusCode;
        DetailCode(String code, String description,HttpStatus statusCode) {
            this.code = code;
            this.description = description;
            this.statusCode = statusCode;
        }

        @Override
        public HttpStatusCode getHttpStatus() {
            return statusCode;
        }

        @Override
        public String getCode() {
            return name();
        }
    }

}
