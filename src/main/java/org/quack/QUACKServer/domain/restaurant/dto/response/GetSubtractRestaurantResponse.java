package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : GetSubtractRestaurantResponse
 * @date : 25. 5. 19.
 */
@Builder
public record GetSubtractRestaurantResponse(
        SubtractRestaurantItem item
) {

    public static GetSubtractRestaurantResponse from(SubtractRestaurantItem item) {
        return GetSubtractRestaurantResponse.builder().item(item).build();
    }
}
