package org.quack.QUACKServer.domain.menu.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public record GetReviewMenusResponse(
        String menuName,
        String menuCategory,
        Long menuPrice,
        String menuImagePath
) {
    public static GetReviewMenusResponse of(String menuName, String menuCategory, Long menuPrice,
                                            String menuImagePath) {
        return GetReviewMenusResponse.builder()
                .menuName(menuName)
                .menuCategory(menuCategory)
                .menuPrice(menuPrice)
                .menuImagePath(menuImagePath)
                .build();
    }
}
