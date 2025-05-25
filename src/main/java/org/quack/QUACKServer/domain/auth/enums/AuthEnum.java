package org.quack.QUACKServer.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.enums
 * @fileName : AuthEnum
 * @date : 25. 5. 25.
 */
public interface AuthEnum {
    @Getter
    @AllArgsConstructor
    enum NicknameValidateCode  {

        USER_NOT_FOUND("user-001", "유저를 찾을 수 없습니다."),
        CURRENT_USE_NICKNAME("user-002", "현재 설정된 닉네임입니다."),
        DUPLICATE_NICKNAME("user-003", "이미 사용중인 닉네임입니다."),
        INVALID_NULL_NICKNAME("user-004", "닉네임은 null일 수 없습니다."),
        INVALID_BLANK_NICKNAME("user-005", "닉네임은 blank일 수 없습니다."),
        INVALID_LENGTH_NICKNAME("user-006", "닉네임은 2자 이상 15자 미만으로 입력해야 합니다."),
        INVALID_PATTERN_NICKNAME("user-007", "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다.");

        private final String value;
        private final String description;

    }
}
