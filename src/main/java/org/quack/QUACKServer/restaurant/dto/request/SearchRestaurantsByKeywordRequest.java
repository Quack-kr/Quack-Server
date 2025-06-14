package org.quack.QUACKServer.restaurant.dto.request;

import org.quack.QUACKServer.user.dto.UserLocationItem;
import org.quack.QUACKServer.core.common.dto.PageInfo;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : SearchRestaurantsByKeywordRequest
 * @date : 25. 5. 24.
 */
public record SearchRestaurantsByKeywordRequest(
        UserLocationItem userLocation,
        String keyword,
        RestaurantSortItem sort,
        PageInfo pageInfo
) {
}
