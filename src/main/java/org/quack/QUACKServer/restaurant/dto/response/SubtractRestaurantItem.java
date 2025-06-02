package org.quack.QUACKServer.restaurant.dto.response;

import lombok.Builder;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.restaurant.vo.RestaurantSubtractByDistanceVo;
import org.quack.QUACKServer.restaurant.vo.RestaurantSubtractByLikeVo;
import org.quack.QUACKServer.restaurant.vo.RestaurantSubtractBySavedVo;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SubtractRestaurantItem
 * @date : 25. 4. 27.
 */
@Builder
public record SubtractRestaurantItem(
        Long restaurantId,
        String restaurantName,
        List<RestaurantEnum.RestaurantCategoryType> restaurantCategory,
        Long distance,
        Long averagePrice,
        Long savedCounts,
        Long likeCounts,
        String openingHour,
        String closingHour,
        List<String> menuImages,
        Boolean isSaved
) {

    public static SubtractRestaurantItem of(RestaurantSubtractByDistanceVo restaurant, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SubtractRestaurantItem.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantCategory(categories)
                .averagePrice(restaurant.getAveragePrice())
                .openingHour(restaurant.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .closingHour(restaurant.getLastOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .isSaved(restaurant.getIsSaved())
                .distance(restaurant.getDistance())
                .build();
    }

    public static SubtractRestaurantItem of(RestaurantSubtractBySavedVo restaurant, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SubtractRestaurantItem.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantCategory(categories)
                .averagePrice(restaurant.getAveragePrice())
                .openingHour(restaurant.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .closingHour(restaurant.getLastOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .isSaved(restaurant.getIsSaved())
                .savedCounts(restaurant.getSavedCount())
                .build();
    }

    public static SubtractRestaurantItem of(RestaurantSubtractByLikeVo restaurant, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SubtractRestaurantItem.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantCategory(categories)
                .averagePrice(restaurant.getAveragePrice())
                .openingHour(restaurant.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .closingHour(restaurant.getLastOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .isSaved(restaurant.getIsSaved())
                .likeCounts(restaurant.getLikedCount())
                .build();
    }
}
