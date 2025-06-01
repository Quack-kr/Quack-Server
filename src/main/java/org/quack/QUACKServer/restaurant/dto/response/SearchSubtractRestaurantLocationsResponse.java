package org.quack.QUACKServer.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchSubtractRestaurantLocationsResponse
 * @date : 25. 5. 19.
 */
@Builder(access = AccessLevel.PRIVATE)
public record SearchSubtractRestaurantLocationsResponse(
        List<SubtractRestaurantLocationItem> items
) {
    public static SearchSubtractRestaurantLocationsResponse from(List<SubtractRestaurantLocationItem> items) {
        return SearchSubtractRestaurantLocationsResponse.builder().items(items).build();
    }
}
