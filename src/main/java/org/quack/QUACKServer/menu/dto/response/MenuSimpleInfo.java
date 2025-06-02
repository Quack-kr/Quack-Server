package org.quack.QUACKServer.menu.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MenuSimpleInfo(
        String menuImage,
        String menuName,
        Long menuPrice
) {
    public static MenuSimpleInfo of(String menuImage, String menuName, Long menuPrice) {
        return MenuSimpleInfo.builder()
                .menuImage(menuImage)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .build();
    }
}
