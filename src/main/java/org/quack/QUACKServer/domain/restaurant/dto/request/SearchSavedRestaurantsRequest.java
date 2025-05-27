package org.quack.QUACKServer.domain.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.global.common.dto.PageInfo;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : SearchSavedRestaurantsRequest
 * @date : 25. 5. 27.
 */
public record SearchSavedRestaurantsRequest(
        @NotNull Double longitude,
        @NotNull Double latitude,
        @NotNull PageInfo pageInfo,
        Boolean isOpen
) {
}
