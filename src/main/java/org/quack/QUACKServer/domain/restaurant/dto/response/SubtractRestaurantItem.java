package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SubtractRestaurantItem
 * @date : 25. 4. 27.
 */
@Builder(access = AccessLevel.PRIVATE)
public record SubtractRestaurantItem(
        Long restaurantId,
        String restaurantName,
        List<RestaurantEnum.RestaurantCategoryType> restaurantCategory,
        Long distance,
        String averagePrice,
        String savedCounts,
        String likeCounts,
        String openingHour,
//        List<String> menuImages,
        Boolean isSaved
) {

//    public static SubtractRestaurantItem from(Restaurant restaurant) {
//        return SubtractRestaurantItem.builder()
//                .restaurantId(restaurant.getRestaurantId())
//                .restaurantName(restaurant.getRestaurantName())
//                .restaurantCategory(restaurant.getCategory().stream().map(RestaurantCategory::getRestaurantCategoryName).toList())
//                .averagePrice()
//                .build();
//    }
}
