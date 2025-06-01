package org.quack.QUACKServer.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.restaurant.vo.RestaurantSubtractByDistanceVo;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SubtractRestaurantLocationItem
 * @date : 25. 5. 19.
 */


@Builder(access = AccessLevel.PRIVATE)
public record SubtractRestaurantLocationItem(
        Long restaurantId,
        Long longitude,
        Long latitude,
        RestaurantEnum.RestaurantCategoryType restaurantCategory,
        Boolean isSaved) {

    public static SubtractRestaurantLocationItem of(RestaurantSubtractByDistanceVo restaurant, RestaurantEnum.RestaurantCategoryType categoryType) {
        return SubtractRestaurantLocationItem.builder()
                .restaurantId(restaurant.getRestaurantId())
                .longitude(restaurant.getLongitude())
                .latitude(restaurant.getLatitude())
                .restaurantCategory(categoryType)
                .isSaved(restaurant.getIsSaved())
                .build();
    }
}
