package org.quack.QUACKServer.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.restaurant.dto.request.SearchSavedRestaurantsRequest;
import org.quack.QUACKServer.restaurant.dto.response.GetSavedRestaurantCountResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSavedRestaurantsInMapResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSavedRestaurantsResponse;
import org.quack.QUACKServer.restaurant.service.MyPageRestaurantService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.quack.QUACKServer.core.error.constant.ErrorCode.UNAUTHORIZED_USER;

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
    public GetSavedRestaurantCountResponse searchSavedRestaurantCount(@AuthenticationPrincipal CustomerUserInfo customerUserInfo) {
        if(customerUserInfo == null) {
            throw new CommonException(UNAUTHORIZED_USER);
        }

        return myPageRestaurantService.getSavedRestaurantCount(customerUserInfo.getCustomerUserId());
    }

    /**
     * 마이페이지 - 저장된 식당 리스트 조회
     */
    @GetMapping("/saved-restaurants")
    public SearchSavedRestaurantsResponse searchSavedRestaurants(
            @AuthenticationPrincipal CustomerUserInfo customerUserInfo,
            @Valid @RequestBody SearchSavedRestaurantsRequest request) {
        if(customerUserInfo == null) {
            throw new CommonException(UNAUTHORIZED_USER);
        }
        return myPageRestaurantService.searchSavedRestaurants(request, customerUserInfo.getCustomerUserId());
    }

    /**
     * 마이페이지 - 저장된 식당 리스트 조회
     */
    @GetMapping("/map/saved-restaurants")
    public SearchSavedRestaurantsInMapResponse searchSavedRestaurantsInMap(
            @AuthenticationPrincipal CustomerUserInfo customerUserInfo,
            @Valid @RequestBody SearchSavedRestaurantsRequest request) {

        if(customerUserInfo == null) {
            throw new CommonException(UNAUTHORIZED_USER);
        }

        return myPageRestaurantService.searchSavedRestaurantsInMap(request, customerUserInfo.getCustomerUserId());
    }
}
