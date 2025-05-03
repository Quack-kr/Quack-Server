package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.user.dto.UserLocationItem;
import org.quack.QUACKServer.global.common.dto.PageInfo;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : GetSubtractRestaurantsRequest
 * @date : 25. 4. 27.
 */


public record SearchSubtractRestaurantsRequest(
        @NotNull RestaurantSubtractFilterItem filter,
        @NotNull RestaurantSortItem sort,
        PageInfo pageInfo
) {
}
