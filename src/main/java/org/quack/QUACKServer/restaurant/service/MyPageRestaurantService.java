package org.quack.QUACKServer.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.PageInfo;
import org.quack.QUACKServer.restaurant.domain.CustomerSavedRestaurant;
import org.quack.QUACKServer.restaurant.dto.request.SearchSavedRestaurantsRequest;
import org.quack.QUACKServer.restaurant.dto.response.GetSavedRestaurantCountResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSavedRestaurantsInMapResponse;
import org.quack.QUACKServer.restaurant.dto.response.SearchSavedRestaurantsItem;
import org.quack.QUACKServer.restaurant.dto.response.SearchSavedRestaurantsResponse;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.restaurant.filter.RestaurantFindDistanceFilter;
import org.quack.QUACKServer.restaurant.repository.CustomerSavedRestaurantRepository;
import org.quack.QUACKServer.restaurant.repository.RestaurantRepositoryImpl;
import org.quack.QUACKServer.restaurant.vo.RestaurantSimpleByDistanceVo;
import org.quack.QUACKServer.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.user.repository.CustomerUserMetadataRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.service
 * @fileName : MyPageRestaurantService
 * @date : 25. 5. 27.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageRestaurantService {

    private final CustomerSavedRestaurantRepository customerSavedRestaurantRepository;
    private final RestaurantRepositoryImpl restaurantRepositoryImpl;
    private final SubtractRestaurantService subtractRestaurantService;
    private final CustomerUserMetadataRepository customerUserMetadataRepository;

    public GetSavedRestaurantCountResponse getSavedRestaurantCount(Long customerUserId) {

        long count = customerSavedRestaurantRepository.countByCustomerUserId(customerUserId);

        return GetSavedRestaurantCountResponse.from(count);
    }


    public SearchSavedRestaurantsResponse searchSavedRestaurants(SearchSavedRestaurantsRequest request, Long customerUserId) {

        Pageable pageable = PageInfo.toPageable(request.pageInfo());

        Slice<CustomerSavedRestaurant> customerSavedRestaurants = customerSavedRestaurantRepository
                .findAllByCustomerUserId(customerUserId, pageable);

        List<Long> restaurantIds = customerSavedRestaurants.getContent().stream()
                .map(CustomerSavedRestaurant::getRestaurantId)
                .toList();

        RestaurantFindDistanceFilter filter = RestaurantFindDistanceFilter.build(
                restaurantIds, request.longitude(), request.latitude(), customerUserId, request.isOpen()
        );

        List<RestaurantSimpleByDistanceVo> restaurantSimpleByDistanceVos = restaurantRepositoryImpl
                .findDistanceByRestaurants(filter);

        Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoriesMap = subtractRestaurantService.getRestaurantCategoryMap(
                restaurantSimpleByDistanceVos.stream()
                        .map(RestaurantSimpleByDistanceVo::getRestaurantId)
                        .toList()
        );
        return SearchSavedRestaurantsResponse.from(
                restaurantSimpleByDistanceVos.stream()
                        .map(vo -> SearchSavedRestaurantsItem.of(vo, categoriesMap.get(vo.getRestaurantId())))
                        .toList()
        );

    }

    // TODO : 전략 패턴으로 수정 가능
    public SearchSavedRestaurantsInMapResponse searchSavedRestaurantsInMap(SearchSavedRestaurantsRequest request, Long customerUserId) {

        CustomerUserMetadata metadata = customerUserMetadataRepository.findById(customerUserId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 읽을 수 없습니다."));

        if(!metadata.getLocationTermsAgreed()) {
            throw new IllegalStateException("위치 정보 동의가 필요합니다.");
        }

        Pageable pageable = PageInfo.toPageable(request.pageInfo());

        Slice<CustomerSavedRestaurant> customerSavedRestaurants = customerSavedRestaurantRepository
                .findAllByCustomerUserId(customerUserId, pageable);

        List<Long> restaurantIds = customerSavedRestaurants.getContent().stream()
                .map(CustomerSavedRestaurant::getRestaurantId)
                .toList();

        RestaurantFindDistanceFilter filter = RestaurantFindDistanceFilter.build(
                restaurantIds, request.longitude(), request.latitude(), customerUserId, request.isOpen()
        );

        List<RestaurantSimpleByDistanceVo> restaurantSimpleByDistanceVos = restaurantRepositoryImpl
                .findDistanceByRestaurants(filter);

        Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoriesMap = subtractRestaurantService.getRestaurantCategoryMap(
                restaurantSimpleByDistanceVos.stream()
                        .map(RestaurantSimpleByDistanceVo::getRestaurantId)
                        .toList()
        );
        return SearchSavedRestaurantsInMapResponse.from(
                restaurantSimpleByDistanceVos.stream()
                        .map(vo -> SearchSavedRestaurantsItem.of(vo, categoriesMap.get(vo.getRestaurantId())))
                        .toList()
        );

    }
}
