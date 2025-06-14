package org.quack.QUACKServer.review.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordType {
    NEGATIVE("NEGATIVE"),
    POSITIVE("POSITIVE");

    private String value;

}
