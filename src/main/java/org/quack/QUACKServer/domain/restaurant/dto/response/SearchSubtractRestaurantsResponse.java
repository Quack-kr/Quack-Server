package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchSubtractRestaurantsResponse
 * @date : 25. 4. 27.
 */

@Builder(access = AccessLevel.PRIVATE)
public record SearchSubtractRestaurantsResponse(
        List<SubtractRestaurantItem> data
) {

    public static SearchSubtractRestaurantsResponse from(List<SubtractRestaurantItem> data) {
        return SearchSubtractRestaurantsResponse.builder().data(data).build();
    }
}
