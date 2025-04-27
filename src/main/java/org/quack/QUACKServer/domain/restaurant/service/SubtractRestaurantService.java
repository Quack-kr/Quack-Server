package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSubtractRestaurantsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.service
 * @fileName : SubtractRestaurantService
 * @date : 25. 4. 27.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SubtractRestaurantService {

    @Transactional(readOnly = true)
    public SearchSubtractRestaurantsResponse searchSubtractRestaurants(SearchSubtractRestaurantsRequest request) {

        // QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        return SearchSubtractRestaurantsResponse.from(new ArrayList<>());

    }
}
