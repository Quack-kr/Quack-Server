package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSimpleByDistanceVo;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchSavedRestaurantsResponse
 * @date : 25. 5. 27.
 */
@Builder(access = AccessLevel.PRIVATE)
public record SearchSavedRestaurantsInMapItem(
        Long restaurantId,
        RestaurantEnum.RestaurantCategoryType category,
        Long distance
) {

    public static SearchSavedRestaurantsInMapItem of(RestaurantSimpleByDistanceVo vo, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SearchSavedRestaurantsInMapItem.builder()
                .restaurantId(vo.getRestaurantId())
                .category(categories.stream().findFirst().orElse(null))
                .distance(vo.getDistance())
                .build();
    }
}
