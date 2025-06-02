package org.quack.QUACKServer.domain.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchRestaurantsByKeywordRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantDetailInfo;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchRestaurantsByKeywordResponse;
import org.quack.QUACKServer.domain.restaurant.service.RestaurantService;
import org.quack.QUACKServer.domain.restaurant.service.SearchRestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/restaurant")
public class RestaurantController {

    private final SearchRestaurantService searchRestaurantService;
    private final RestaurantService restaurantService;

    /**
     * 검색 기능
     */
    @GetMapping("/search/restaurants")
    public SearchRestaurantsByKeywordResponse searchSubtractRestaurants(@RequestBody(required = false) @Valid SearchRestaurantsByKeywordRequest request) {
        return searchRestaurantService.searchRestaurantByName(request);
    }

    @GetMapping("/{restaurantId}")
    public GetRestaurantDetailInfo getRestaurantDetails(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantDetailInfo(restaurantId);
    }
}
