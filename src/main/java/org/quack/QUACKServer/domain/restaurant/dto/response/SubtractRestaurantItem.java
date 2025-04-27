package org.quack.QUACKServer.domain.restaurant.dto.response;

import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SubtractRestaurantItem
 * @date : 25. 4. 27.
 */
public record SubtractRestaurantItem(
        Long restaurantId,
        String restaurantName,
        RestaurantEnum.RestaurantCategoryType restaurantCategory,
        Long distance,
        String averagePrice,
        String openingHour,
        List<String> menuImages,
        Boolean isSaved
) {
}
