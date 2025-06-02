package org.quack.QUACKServer.restaurant.filter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantFindDistanceFilter
 * @date : 25. 5. 27.
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RestaurantFindDistanceFilter {
    private List<Long> restaurantIds;
    private Double longitude;
    private Double latitude;
    private Long customerUserId;
    private boolean isOpen;
    @Builder(access = AccessLevel.PRIVATE)
    public static RestaurantFindDistanceFilter build(
            List<Long> restaurantIds, Double longitude, Double latitude, Long customerUserId, boolean isOpen
    ) {

        return RestaurantFindDistanceFilter.builder()
                .restaurantIds(restaurantIds)
                .longitude(longitude)
                .latitude(latitude)
                .customerUserId(customerUserId)
                .isOpen(isOpen)
                .build();

    }

}
