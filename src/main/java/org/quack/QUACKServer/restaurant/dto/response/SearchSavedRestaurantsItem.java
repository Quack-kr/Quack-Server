package org.quack.QUACKServer.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.restaurant.vo.RestaurantSimpleByDistanceVo;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchSavedRestaurantsResponse
 * @date : 25. 5. 27.
 */
@Builder(access = AccessLevel.PRIVATE)
public record SearchSavedRestaurantsItem(
        Long restaurantId,
        List<RestaurantEnum.RestaurantCategoryType> categories,
        String description,
        Long distance,
        Long averagePrice,
        Boolean isOpen,
        Long restaurantPhotoId
) {

    public static SearchSavedRestaurantsItem of(RestaurantSimpleByDistanceVo vo,  List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SearchSavedRestaurantsItem.builder()
                .restaurantId(vo.getRestaurantId())
                .categories(categories)
                .description(vo.getSimpleDescription())
                .distance(vo.getDistance())
                .averagePrice(vo.getAveragePrice())
                .isOpen(vo.getIsOpen())
                .build();
    }
}
