package org.quack.QUACKServer.global.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.enums
 * @fileName : ProviderType
 * @date : 25. 4. 15.
 */

@Getter
@AllArgsConstructor
public enum ProviderType {

    APPLE("APPLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;

    public static ProviderType from(String value) {
        return Arrays.stream(ProviderType.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
