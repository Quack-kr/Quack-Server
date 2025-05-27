package org.quack.QUACKServer.domain.restaurant.dto.response;

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
public record SearchSavedRestaurantsResponse(
        List<SearchSavedRestaurantsItem> items
) {

    public static SearchSavedRestaurantsResponse from(List<SearchSavedRestaurantsItem> items) {
        return SearchSavedRestaurantsResponse.builder()
                .items(items)
                .build();
    }
}
