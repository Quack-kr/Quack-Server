package org.quack.QUACKServer.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum KeywordType {
    NEGATIVE, POSITIVE;


    public static KeywordType from(String value) {
        return KeywordType.valueOf(value.toUpperCase());
    }
}
