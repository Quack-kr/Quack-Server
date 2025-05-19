package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.global.common.dto.PageInfo;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : GetSubtractRestaurantsRequest
 * @date : 25. 4. 27.
 */


public record SearchSubtractRestaurantsNoAuthRequest(
        RestaurantSubtractFilterItem filter,
        @NotNull RestaurantSortItem sort,
        PageInfo pageInfo
) {

}
