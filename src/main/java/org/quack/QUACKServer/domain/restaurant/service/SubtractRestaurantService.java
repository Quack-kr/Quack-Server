package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
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

        // 1. 필터링 처리

        // 2. 정렬조건 처리

        // 3. 페이징 정보 처리

        // 4. 조회

        // 5. Vo to Dto
        return SearchSubtractRestaurantsResponse.from(new ArrayList<>());

    }

}
