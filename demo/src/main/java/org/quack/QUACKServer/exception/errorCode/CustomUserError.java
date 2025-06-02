package org.quack.QUACKServer.exception.errorCode;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomUserError implements ErrorCode {

    USER_NOT_FOUND("user-001", "유저를 찾을 수 없습니다.", BAD_REQUEST),
    CURRENT_USE_NICKNAME("user-002", "현재 설정된 닉네임입니다.", BAD_REQUEST),
    DUPLICATE_NICKNAME("user-003", "이미 사용중인 닉네임입니다.", CONFLICT),
    INVALID_NULL_NICKNAME("user-004", "닉네임은 null일 수 없습니다.", BAD_REQUEST),
    INVALID_BLANK_NICKNAME("user-005", "닉네임은 blank일 수 없습니다.", BAD_REQUEST),
    INVALID_LENGTH_NICKNAME("user-006", "닉네임은 2자 이상 15자 미만으로 입력해야 합니다.", BAD_REQUEST),
    INVALID_PATTERN_NICKNAME("user-007", "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다.", BAD_REQUEST);


    private final String code;
    private final String description;
    private final HttpStatus statusCode;

}
