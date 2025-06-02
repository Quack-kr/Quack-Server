package org.quack.QUACKServer.restaurant.dto.request;

import org.quack.QUACKServer.user.dto.UserLocationItem;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : SearchSubtractRestaurantLocationsRequest
 * @date : 25. 5. 19.
 */
public record SearchSubtractRestaurantLocationsRequest(
        UserLocationItem userLocationItem
) {
}
