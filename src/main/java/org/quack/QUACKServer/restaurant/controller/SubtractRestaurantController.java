package org.quack.QUACKServer.restaurant.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.restaurant.dto.request.SearchSubtractRestaurantLocationsRequest;
import org.quack.QUACKServer.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.restaurant.dto.response.GetSubtractRestaurantResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSubtractRestaurantLocationsResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSubtractRestaurantsResponse;
import org.quack.QUACKServer.restaurant.service.SubtractRestaurantService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.quack.QUACKServer.core.error.constant.ErrorCode.UNAUTHORIZED_USER;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.controller
 * @fileName : SubtractRestaurantController
 * @date : 25. 4. 27.
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/subtract")
public class SubtractRestaurantController {

    private final SubtractRestaurantService baseSubtractRestaurantService;

    /**
     * 빼기 - 식당 조회 (필터, 정렬)
     */
    @GetMapping("/restaurants")
    public SearchSubtractRestaurantsResponse searchSubtractRestaurants(
            @AuthenticationPrincipal CustomerUserInfo userInfo,
            @Valid SearchSubtractRestaurantsRequest request) {
        return baseSubtractRestaurantService.searchSubtractRestaurants(request, userInfo);
    }

    /**
     * 빼기 - 식당 조회 (필터, 정렬) - (둘러보기 권한)
     */
    @GetMapping("/public/restaurants")
    public SearchSubtractRestaurantsResponse searchSubtractRestaurantsByNoAuth(
            @Valid SearchSubtractRestaurantsRequest request) {
        return baseSubtractRestaurantService.searchSubtractRestaurants(request, null);
    }

    /**
     * 빼기(지도) - 식당 조회
     */
    @GetMapping("/restaurants-locations")
    public SearchSubtractRestaurantLocationsResponse searchSubtractRestaurantLocations(
            @AuthenticationPrincipal CustomerUserInfo customerUserInfo,
            @Valid SearchSubtractRestaurantLocationsRequest request) {
        if(customerUserInfo == null) {
            throw new CommonException(UNAUTHORIZED_USER);
        }
        return baseSubtractRestaurantService.searchSubtractRestaurantLocations(request, customerUserInfo.getCustomerUserId());
    }

    /**
     * 빼기(지도) - 식당 단일 조회
     */
    @GetMapping("/restaurant/{restaurantId}")
    public GetSubtractRestaurantResponse getSubtractRestaurant(@NotNull @PathVariable Long restaurantId) {
        return baseSubtractRestaurantService.getSubtractRestaurant(restaurantId);
    }
}
