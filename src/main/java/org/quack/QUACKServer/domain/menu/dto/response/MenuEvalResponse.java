package org.quack.QUACKServer.domain.menu.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.menu.enums.MenuEnum;

@Builder(access = AccessLevel.PRIVATE)
public record MenuEvalResponse(
        String menuName,
        MenuEnum.MenuEvalType evaluation
) {
    public static MenuEvalResponse of(String menuName, MenuEnum.MenuEvalType evaluation) {
        return MenuEvalResponse.builder()
                .menuName(menuName)
                .evaluation(evaluation)
                .build();
    }
}
