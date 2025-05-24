package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchRestaurantsByKeywordRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchRestaurantsByKeywordItem;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchRestaurantsByKeywordResponse;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSearchFilter;
import org.quack.QUACKServer.domain.restaurant.repository.RestaurantRepositoryImpl;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchByDistanceVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchByLikeVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchBySavedVo;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.service
 * @fileName : SearchRestaurantService
 * @date : 25. 5. 24.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SearchRestaurantService {


    private final RestaurantRepositoryImpl restaurantRepositoryImpl;
    private final SubtractRestaurantService subtractRestaurantService;

    public SearchRestaurantsByKeywordResponse searchRestaurantByName(SearchRestaurantsByKeywordRequest request) {

        RestaurantSearchFilter filter = RestaurantSearchFilter.from(request);

        List<SearchRestaurantsByKeywordItem> items = new ArrayList<>();

        switch (filter.getSortType()) {
            case LIKE -> {
                Slice<RestaurantSearchByLikeVo> restaurants = restaurantRepositoryImpl.findByRestaurantNameOrderByLike(filter);

                Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoriesMap = subtractRestaurantService.getRestaurantCategoryMap(
                        restaurants.getContent().stream()
                                .map(RestaurantSearchByLikeVo::getRestaurantId)
                                .toList()
                );

                items = restaurants.stream()
                        .map(item -> SearchRestaurantsByKeywordItem.of(item, categoriesMap.get(item.getRestaurantId())))
                        .toList();
            }
            case DISTANCE -> {
                Slice<RestaurantSearchByDistanceVo> restaurants = restaurantRepositoryImpl.findByRestaurantNameOrderByDistance(filter);

                Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoriesMap = subtractRestaurantService.getRestaurantCategoryMap(
                        restaurants.getContent().stream()
                                .map(RestaurantSearchByDistanceVo::getRestaurantId)
                                .toList()
                );

                items = restaurants.stream()
                        .map(item -> SearchRestaurantsByKeywordItem.of(item, categoriesMap.get(item.getRestaurantId())))
                        .toList();
            }
            case SAVED -> {
                Slice<RestaurantSearchBySavedVo> restaurants = restaurantRepositoryImpl.findByRestaurantNameOrderBySaved(filter);

                Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoriesMap = subtractRestaurantService.getRestaurantCategoryMap(
                        restaurants.getContent().stream()
                                .map(RestaurantSearchBySavedVo::getRestaurantId)
                                .toList()
                );

                items = restaurants.stream()
                        .map(item -> SearchRestaurantsByKeywordItem.of(item, categoriesMap.get(item.getRestaurantId())))
                        .toList();

            }
        }

        return SearchRestaurantsByKeywordResponse.from(items);

    }

}
