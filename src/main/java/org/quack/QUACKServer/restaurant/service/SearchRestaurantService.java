package org.quack.QUACKServer.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.restaurant.dto.request.SearchRestaurantsByKeywordRequest;
import org.quack.QUACKServer.restaurant.dto.response.SearchRestaurantsByKeywordItem;
import org.quack.QUACKServer.restaurant.dto.response.SearchRestaurantsByKeywordResponse;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.restaurant.filter.RestaurantSearchFilter;
import org.quack.QUACKServer.restaurant.repository.RestaurantRepositoryImpl;
import org.quack.QUACKServer.restaurant.vo.RestaurantSearchByDistanceVo;
import org.quack.QUACKServer.restaurant.vo.RestaurantSearchByLikeVo;
import org.quack.QUACKServer.restaurant.vo.RestaurantSearchBySavedVo;
import org.quack.QUACKServer.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.user.repository.CustomerUserMetadataRepository;
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
    private final CustomerUserMetadataRepository customerUserMetadataRepository;


    private final RestaurantRepositoryImpl restaurantRepositoryImpl;
    private final SubtractRestaurantService subtractRestaurantService;

    public SearchRestaurantsByKeywordResponse searchRestaurantByName(SearchRestaurantsByKeywordRequest request, Long customerUserId) {

        if(RestaurantEnum.RestaurantSortType.DISTANCE.equals(request.sort().sortType()) && customerUserId != null) {
            CustomerUserMetadata metadata = customerUserMetadataRepository.findById(customerUserId)
                    .orElseThrow(() -> new RuntimeException("서버 에러 발생"));

            if(!metadata.getLocationTermsAgreed()) {
                throw new IllegalArgumentException("위치 체크를 확인해주세요.");
            }

            if ((request.userLocation() == null
                    || (request.userLocation().longitude() == null && request.userLocation().latitude() == null))) {
                throw new IllegalArgumentException("사용자 위치를 읽을 수 없습니다.");
            }
        }

        RestaurantSearchFilter filter = RestaurantSearchFilter.of(request, customerUserId);

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
