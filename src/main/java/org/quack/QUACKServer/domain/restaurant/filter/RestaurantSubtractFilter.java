package org.quack.QUACKServer.domain.restaurant.filter;

import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.filter
 * @fileName : RestaurantSubtractFilter
 * @date : 25. 4. 29.
 */

@Getter
@Builder
public class RestaurantSubtractFilter {

    private Double longitude;
    private Double latitude;
    private RestaurantEnum.RestaurantSortType sortType;
    private boolean isOpen;

    public static RestaurantSubtractFilter from(SearchSubtractRestaurantsRequest request) {

        RestaurantEnum.RestaurantSortType sorType = request.sort().orderByType();

        if(sorType != null
                && sorType.equals(RestaurantEnum.RestaurantSortType.DISTANCE)
                && request.sort().userLocationItem().longitude() == null
                && request.sort().userLocationItem().latitude() == null) {
            throw new IllegalArgumentException("사용자 위치를 읽을 수 없습니다.");
        }

        return RestaurantSubtractFilter.builder()
                .longitude(request.sort().userLocationItem().longitude())
                .latitude(request.sort().userLocationItem().latitude())
                .sortType(request.sort().orderByType())
                .isOpen(request.sort().isOpen())
                .build();

    }
}
