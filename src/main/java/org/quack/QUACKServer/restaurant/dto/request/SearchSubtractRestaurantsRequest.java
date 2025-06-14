package org.quack.QUACKServer.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.user.dto.UserLocationItem;
import org.quack.QUACKServer.core.common.dto.PageInfo;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : GetSubtractRestaurantsRequest
 * @date : 25. 4. 27.
 */


public record SearchSubtractRestaurantsRequest(
        RestaurantSubtractFilterItem filter,
        @NotNull RestaurantSortItem sort,
        UserLocationItem userLocationItem,
        PageInfo pageInfo
) {

}
