package org.quack.QUACKServer.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewType {
    FULL("FULL"),
    SIMPLE("SIMPLE");

    private final String value;
}
