package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto
 * @fileName : RestaurantOrderByItem
 * @date : 25. 4. 27.
 */

public record RestaurantOrderByItem(@NotNull RestaurantEnum.RestaurantOrderByType orderByType,
                                    @NotNull Boolean isOpen) {

}
