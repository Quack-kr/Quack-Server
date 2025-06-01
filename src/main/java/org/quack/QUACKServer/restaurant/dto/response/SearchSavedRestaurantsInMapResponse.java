package org.quack.QUACKServer.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchSavedRestaurantsResponse
 * @date : 25. 5. 27.
 */

@Builder(access = AccessLevel.PRIVATE)
public record SearchSavedRestaurantsInMapResponse(
        List<SearchSavedRestaurantsItem> items
) {

    public static SearchSavedRestaurantsInMapResponse from(List<SearchSavedRestaurantsItem> items) {
        return SearchSavedRestaurantsInMapResponse.builder()
                .items(items)
                .build();
    }
}
