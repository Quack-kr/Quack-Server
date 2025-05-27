package org.quack.QUACKServer.domain.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSavedRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetSavedRestaurantCountResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSavedRestaurantsInMapResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSavedRestaurantsResponse;
import org.quack.QUACKServer.domain.restaurant.service.MyPageRestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.controller
 * @fileName : MyPageRestaurantController
 * @date : 25. 5. 27.
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/my-page")
public class MyPageRestaurantController {

    private final MyPageRestaurantService myPageRestaurantService;

    /**
     * 마이페이지 - 저장된 식당 개수 조회
     */
    @GetMapping("/saved-restaurant-count")
    public GetSavedRestaurantCountResponse searchSavedRestaurantCount() {
        return myPageRestaurantService.getSavedRestaurantCount();
    }

    /**
     * 마이페이지 - 저장된 식당 리스트 조회
     */
    @GetMapping("/saved-restaurants")
    public SearchSavedRestaurantsResponse searchSavedRestaurants(
            @Valid @RequestBody SearchSavedRestaurantsRequest request) {
        return myPageRestaurantService.searchSavedRestaurants(request);
    }

    /**
     * 마이페이지 - 저장된 식당 리스트 조회
     */
    @GetMapping("/map/saved-restaurants")
    public SearchSavedRestaurantsInMapResponse searchSavedRestaurantsInMap(
            @Valid @RequestBody SearchSavedRestaurantsRequest request) {
        return myPageRestaurantService.searchSavedRestaurantsInMap(request);
    }
}
