package org.quack.QUACKServer.restaurant.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.core.io.Resource;

@Builder(access = AccessLevel.PRIVATE)
public record GetRestaurantInfoResponse(
        String restaurantName,
        String restaurantDetailAddress,
        List<String> restaurantCategory,
        String restaurantImage
) {
    public static GetRestaurantInfoResponse of(String restaurantName, String restaurantDetailAddress,
                                                 List<String> restaurantCategory, String restaurantImage) {
        return GetRestaurantInfoResponse.builder()
                .restaurantName(restaurantName)
                .restaurantDetailAddress(restaurantDetailAddress)
                .restaurantCategory(restaurantCategory)
                .restaurantImage(restaurantImage)
                .build();
    }
}
