package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchRestaurantsByKeywordResponse
 * @date : 25. 5. 24.
 */
@Builder(access = AccessLevel.PRIVATE)
public record SearchRestaurantsByKeywordResponse(
    List<SearchRestaurantsByKeywordItem> items
) {
    public static SearchRestaurantsByKeywordResponse from(List<SearchRestaurantsByKeywordItem> items) {
        return SearchRestaurantsByKeywordResponse.builder().items(items).build();
    }
}
