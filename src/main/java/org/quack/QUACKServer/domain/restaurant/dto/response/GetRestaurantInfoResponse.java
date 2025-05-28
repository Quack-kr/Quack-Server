package org.quack.QUACKServer.domain.restaurant.dto.response;

import java.util.List;

public record GetRestaurantBasicInfoResponse(
        String restaurantName,
        String restaurantDetailAddress,
        List<String> restaurantCategory
) {
}
