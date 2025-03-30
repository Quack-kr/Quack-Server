package org.quack.QUACKServer.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.global.common.constans.QuackCode;
import org.quack.QUACKServer.global.common.exception.BaseCustomException;

import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security
 * @fileName : LoginType
 * @date : 25. 3. 29.
 */

@Getter
@AllArgsConstructor
public enum LoginType {
    KAKAO("kakao", "카카오 로그인 사용자"),
    NAVER("naver", "네이버 로그인 사용자"),
    APPLE("apple", "애플 로그인 사용자"),
    ;

    private final String typeName;
    private final String description;

    public static LoginType of(String clientName) {
        return Arrays.stream(values())
                .filter(type -> type.getTypeName().equalsIgnoreCase(clientName))
                .findFirst()
                .orElseThrow(() -> new BaseCustomException(QuackCode.DetailCode.USER_NOT_FOUND));
    }

}
