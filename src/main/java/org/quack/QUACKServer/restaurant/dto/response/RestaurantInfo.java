package org.quack.QUACKServer.restaurant.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.geo.Point;

@Builder(access = AccessLevel.PRIVATE)
public record RestaurantInfo(
        String restaurantName,
        List<String> restaurantCategories,
        List<String> restaurantArea,
        String restaurantAddress,
        Point location,
        String simpleDescription,
        String detailDescription,
        String effortMessage
) {
    public static RestaurantInfo of(String restaurantName, List<String> restaurantCategories,
                                    List<String> restaurantArea, String restaurantAddress, Point location,
                                    String simpleDescription, String detailDescription, String effortMessage) {
        return RestaurantInfo.builder()
                .restaurantName(restaurantName)
                .restaurantCategories(restaurantCategories)
                .restaurantArea(restaurantArea)
                .restaurantAddress(restaurantAddress)
                .location(location)
                .simpleDescription(simpleDescription)
                .detailDescription(detailDescription)
                .effortMessage(effortMessage)
                .build();
    }
}
