package org.quack.QUACKServer.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.dto
 * @fileName : GetReviewMyCountResponse
 * @date : 25. 5. 27.
 */

@Builder(access = AccessLevel.PRIVATE)
public record GetSavedRestaurantCountResponse(
        Long savedRestaurantCount
) {

    public static GetSavedRestaurantCountResponse from(Long savedRestaurantCount) {
        return GetSavedRestaurantCountResponse.builder().savedRestaurantCount(savedRestaurantCount).build();
    }
}
