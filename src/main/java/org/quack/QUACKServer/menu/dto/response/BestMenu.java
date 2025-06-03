package org.quack.QUACKServer.menu.dto.response;


import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.menu.enums.MenuEnum.MenuEvalType;


@Builder(access = AccessLevel.PRIVATE)
public record BestMenu(
        Integer ranking,
        String menuName,
        Map<MenuEvalType, Long> evaluation
) {
    public static BestMenu of(Integer ranking, String menuName, Map<MenuEvalType, Long> evaluation) {
        return BestMenu.builder()
                .ranking(ranking)
                .menuName(menuName)
                .evaluation(evaluation)
                .build();
    }
}
