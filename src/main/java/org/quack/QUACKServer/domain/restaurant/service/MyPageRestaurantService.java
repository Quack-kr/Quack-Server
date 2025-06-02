package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.restaurant.domain.CustomerSavedRestaurant;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSavedRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetSavedRestaurantCountResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSavedRestaurantsInMapResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSavedRestaurantsItem;
import org.quack.QUACKServer.domain.restaurant.dto.response.SearchSavedRestaurantsResponse;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantFindDistanceFilter;
import org.quack.QUACKServer.domain.restaurant.repository.CustomerSavedRestaurantRepository;
import org.quack.QUACKServer.domain.restaurant.repository.RestaurantRepositoryImpl;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSimpleByDistanceVo;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.domain.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.global.common.dto.PageInfo;
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

    public GetSavedRestaurantCountResponse getSavedRestaurantCount() {

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 불가 합니다.");
        }

        long count = customerSavedRestaurantRepository.countByCustomerUserId(QuackAuthContext.getCustomerUserId());

        return GetSavedRestaurantCountResponse.from(count);
    }


    public SearchSavedRestaurantsResponse searchSavedRestaurants(SearchSavedRestaurantsRequest request) {

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비 로그인 시에는 접근이 불가 합니다.");
        }

        Pageable pageable = PageInfo.toPageable(request.pageInfo());

        Slice<CustomerSavedRestaurant> customerSavedRestaurants = customerSavedRestaurantRepository
                .findAllByCustomerUserId(quackUser.getCustomerUserId(), pageable);

        List<Long> restaurantIds = customerSavedRestaurants.getContent().stream()
                .map(CustomerSavedRestaurant::getRestaurantId)
                .toList();

        RestaurantFindDistanceFilter filter = RestaurantFindDistanceFilter.build(
                restaurantIds, request.longitude(), request.latitude(), quackUser.getCustomerUserId(), request.isOpen()
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
    public SearchSavedRestaurantsInMapResponse searchSavedRestaurantsInMap(SearchSavedRestaurantsRequest request) {
        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        CustomerUserMetadata metadata = customerUserMetadataRepository.findById(quackUser.getCustomerUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 읽을 수 없습니다."));

        if(!metadata.getLocationTermsAgreed()) {
            throw new IllegalStateException("위치 정보 동의가 필요합니다.");
        }

        Pageable pageable = PageInfo.toPageable(request.pageInfo());

        Slice<CustomerSavedRestaurant> customerSavedRestaurants = customerSavedRestaurantRepository
                .findAllByCustomerUserId(quackUser.getCustomerUserId(), pageable);

        List<Long> restaurantIds = customerSavedRestaurants.getContent().stream()
                .map(CustomerSavedRestaurant::getRestaurantId)
                .toList();

        RestaurantFindDistanceFilter filter = RestaurantFindDistanceFilter.build(
                restaurantIds, request.longitude(), request.latitude(), quackUser.getCustomerUserId(), request.isOpen()
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

    public boolean checkSavedRestaurant(Long restaurantId) {
        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        if(QuackAuthContext.isAnonymous()) {
            return false;
        }

        return customerSavedRestaurantRepository.existsByCustomerUserIdAndRestaurantId(
                quackUser.getCustomerUserId(), restaurantId);
    }
}
