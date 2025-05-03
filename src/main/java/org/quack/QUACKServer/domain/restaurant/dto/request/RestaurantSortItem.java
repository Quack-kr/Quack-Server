package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.user.dto.UserLocationItem;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto
 * @fileName : RestaurantSortItem
 * @date : 25. 4. 27.
 */

public record RestaurantSortItem(
        UserLocationItem userLocationItem,
        @NotNull RestaurantEnum.RestaurantSortType orderByType,
        @NotNull Boolean isOpen) {

}
