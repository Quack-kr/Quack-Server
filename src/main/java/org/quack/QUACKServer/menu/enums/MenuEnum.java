package org.quack.QUACKServer.menu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface MenuEnum {

    @Getter
    @AllArgsConstructor
    enum MenuEvalType {

        CRAZY("CRAZY", "미친맛"),
        DELICIOUS("DELICIOUS", "맛있어요"),
        AVERAGE("AVERAGE", "평범해요"),
        DISAPPOINT("DISAPPOINT", "아쉬워요"),
        TERRIBLE("TERRIBLE", "핵노맛")
        ;

        private final String value;
        private final String description;
    }
}
