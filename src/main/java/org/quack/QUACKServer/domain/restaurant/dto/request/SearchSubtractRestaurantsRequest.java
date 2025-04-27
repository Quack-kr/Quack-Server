package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : GetSubtractRestaurantsRequest
 * @date : 25. 4. 27.
 */


public record SearchSubtractRestaurantsRequest(
        @NotNull
        List<RestaurantEnum.RestaurantKeyword> filterBy,
        @NotNull
        List<RestaurantOrderByItem> orderBy
) {
}
