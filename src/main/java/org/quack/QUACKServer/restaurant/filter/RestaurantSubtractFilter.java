package org.quack.QUACKServer.restaurant.filter;

import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.restaurant.dto.request.SearchSubtractRestaurantLocationsRequest;
import org.quack.QUACKServer.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.springframework.data.domain.Pageable;

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
    private Boolean isSaved;

    // 식당 관련 빼기 필터
    private Boolean isUniSexToilet;
    private List<RestaurantEnum.ParkingType> parkingTypes;
    private List<RestaurantEnum.RestaurantCategoryType> restaurantCategoryTypes;
    private Long customerUserId;

    // 리뷰 빼기 필터
    private List<ReviewEnum.ReviewTag> reviewTags;

    // 정렬 필터링
    private Pageable pageable;

    public static RestaurantSubtractFilter from(SearchSubtractRestaurantsRequest request) {

        return RestaurantSubtractFilter.builder()
                .longitude(request.userLocationItem() != null ? request.userLocationItem().longitude() : null)
                .latitude(request.userLocationItem() != null ? request.userLocationItem().latitude() : null)
                .sortType(request.sort().sortType())
                .isOpen(request.sort().isOpen())
                .customerUserId(PrincipalManager.getCustomerUserId() == null ? PrincipalManager.getCustomerUserId() : 0L)
                .build();

    }

    public static RestaurantSubtractFilter from(SearchSubtractRestaurantLocationsRequest request) {
        return RestaurantSubtractFilter.builder()
                .longitude(request.userLocationItem() != null ? request.userLocationItem().longitude() : null)
                .latitude(request.userLocationItem() != null ? request.userLocationItem().latitude() : null)
                .sortType(RestaurantEnum.RestaurantSortType.DISTANCE)
                .isSaved(true)
                .customerUserId(PrincipalManager.getCustomerUserId())
                .build();
    }
}
