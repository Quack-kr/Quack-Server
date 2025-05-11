package org.quack.QUACKServer.domain.restaurant.filter;

import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.filter
 * @fileName : RestaurantSubtractFilter
 * @date : 25. 4. 29.
 */

@Getter
@Builder(toBuilder = true)
public class RestaurantSubtractFilter {

    // 정렬 필터
    private Double longitude;
    private Double latitude;
    private RestaurantEnum.RestaurantSortType sortType;
    private Boolean isOpen;
    // 식당 관련 빼기 필터
    private Boolean isUniSexToilet;
    private List<RestaurantEnum.ParkingType> parkingTypes;
    private List<RestaurantEnum.RestaurantCategoryType> restaurantCategoryTypes;
    // TODO : 서비스 유형 필터
    // private List<String> serviceTypes;

    // 리뷰 빼기 필터
    private List<ReviewEnum.ReviewTag> reviewTags;


    public static RestaurantSubtractFilter from(SearchSubtractRestaurantsRequest request) {

        return RestaurantSubtractFilter.builder()
                .longitude(request.sort().userLocationItem().longitude())
                .latitude(request.sort().userLocationItem().latitude())
                .sortType(request.sort().sortType())
                .isOpen(request.sort().isOpen())
                .build();

    }
}
