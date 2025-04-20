package org.quack.QUACKServer.global.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.enums
 * @fileName : ClientType
 * @date : 25. 4. 15.
 */

@Getter
@AllArgsConstructor
public enum ClientType {

    APPLE("APPLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;

    public static ClientType from(String value) {
        return Arrays.stream(ClientType.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

    }
}
