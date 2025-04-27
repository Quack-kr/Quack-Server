package org.quack.QUACKServer.domain.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSubtractRestaurantsResponse;
import org.quack.QUACKServer.domain.restaurant.service.SubtractRestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final SubtractRestaurantService subtractRestaurantService;

    /**
     * 빼기 - 식당 조회 (필터, 정렬)
     */
    @GetMapping("/restaurants")
    public SearchSubtractRestaurantsResponse searchSubtractRestaurants(@Valid SearchSubtractRestaurantsRequest request) {
        return subtractRestaurantService.searchSubtractRestaurants(request);
    }

    /**
     * 빼기 - 식당 조회 (필터, 정렬) - (둘러보기 권한)
     * TODO : 우선 API 만 분리
     */
    @GetMapping("/public/restaurants")
    public SearchSubtractRestaurantsResponse searchSubtractRestaurantsByNoAuth(@Valid SearchSubtractRestaurantsRequest request) {
        return subtractRestaurantService.searchSubtractRestaurants(request);
    }
}
