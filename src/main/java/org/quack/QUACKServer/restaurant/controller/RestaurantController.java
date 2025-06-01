package org.quack.QUACKServer.restaurant.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.restaurant.dto.request.SearchRestaurantsByKeywordRequest;
import org.quack.QUACKServer.restaurant.dto.response.SearchRestaurantsByKeywordResponse;
import org.quack.QUACKServer.restaurant.service.RestaurantService;
import org.quack.QUACKServer.restaurant.service.SearchRestaurantService;
import org.springframework.web.bind.annotation.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.controller
 * @fileName : RestaurantController
 * @date : 25. 5. 24.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("")
public class RestaurantController {

    private final SearchRestaurantService searchRestaurantService;
    private final RestaurantService restaurantService;

    /**
     * 검색 기능
     */
    @GetMapping("/restaurant/search/restaurants")
    public SearchRestaurantsByKeywordResponse searchSubtractRestaurants(@RequestBody(required = false) @Valid SearchRestaurantsByKeywordRequest request) {
        return searchRestaurantService.searchRestaurantByName(request);
    }


    /**
     * 식당 저장
     */
    @PostMapping("/my-restaurant/update")
    public ResponseDto<?> updateCustomerUserRestaurant(@RequestBody @Valid @NotNull Long restaurantId) {
        return restaurantService.updateCustomerUserRestaurant(restaurantId);
    }
}
