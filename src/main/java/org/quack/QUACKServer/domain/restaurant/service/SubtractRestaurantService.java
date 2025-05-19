package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantMetadata;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantOwnerMetadata;
import org.quack.QUACKServer.domain.restaurant.dto.request.RestaurantSubtractFilterItem;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantLocationsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.request.SearchSubtractRestaurantsRequest;
import org.quack.QUACKServer.domain.restaurant.dto.response.*;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;
import org.quack.QUACKServer.domain.restaurant.repository.*;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractByDistanceVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractByLikeVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractBySavedVo;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.AtcFilterType.*;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.AtmosphereFilterType.ATMOSPHERE_LACKING;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.AtmosphereFilterType.NOISY;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.FavorFilterType.*;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ServiceFilterType.UNFRIENDLY;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ToiletFilterType.DIRTY;
import static org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ToiletFilterType.UNISEX;
import static org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag.MEAT_SERVICE;
import static org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag.WAITING_TIME;

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
    private final RestaurantRepositoryImpl restaurantRepositoryImpl;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMetadataRepository restaurantMetadataRepository;
    private final RestaurantOwnerMetadataRepository restaurantOwnerMetadataRepository;

    @Transactional(readOnly = true)
    public SearchSubtractRestaurantsResponse searchSubtractRestaurants(SearchSubtractRestaurantsRequest request) {

        if (RestaurantEnum.RestaurantSortType.DISTANCE.equals(request.sort().sortType())
                && (request.userLocationItem() == null
                || (request.userLocationItem().longitude() == null && request.userLocationItem().latitude() == null))) {
            throw new IllegalArgumentException("사용자 위치를 읽을 수 없습니다.");
        }

        RestaurantSubtractFilter filter = RestaurantSubtractFilter.from(request);

        // 1. 필터링 처리
        if (request.filter() != null) {
            // 1-1. 식당 관련 필터링(카테고리)
            List<RestaurantEnum.RestaurantCategoryType> restaurantCategoryTypes = CollectionUtils.isEmpty(request.filter().restaurantCategoryTypes())
                    ? List.of() : request.filter().restaurantCategoryTypes();

            // 1-2. 식당 관련 필터링(주차)
            List<RestaurantEnum.ParkingType> parkingTypes = CollectionUtils.isEmpty(request.filter().serviceFilterTypes())
                    ? List.of()
                    : request.filter().serviceFilterTypes().stream()
                    .map(RestaurantEnum.ParkingType::fromSubtractFilter)
                    .filter(Objects::nonNull)
                    .toList();

            // 1-3. 식당 관련 필터링(남녀 공용 화장실)
            Boolean isUniSexToilet = CollectionUtils.isEmpty(request.filter().toiletFilterTypes())
                    || !request.filter().toiletFilterTypes().contains(UNISEX);

            // 1-5. 리뷰 태그 필터링(3개월간 식당 말까? Top 3 여부에 따른 필터링 여부)
            List<ReviewEnum.ReviewTag> reviewTags = convertToReviewTag(request.filter());

            filter = filter.toBuilder()
                    .restaurantCategoryTypes(restaurantCategoryTypes)
                    .parkingTypes(parkingTypes)
                    .isUniSexToilet(isUniSexToilet)
                    .reviewTags(reviewTags)
                    .build();
        }

        List<SubtractRestaurantItem> subtractRestaurantItems = new ArrayList<>();

        switch (request.sort().sortType()) {
            case DISTANCE -> {
                subtractRestaurantItems = getSubtractRestaurantItemsByDistance(filter);
            }
            case LIKE -> {
                List<RestaurantSubtractByLikeVo> restaurants = restaurantRepositoryImpl.findAllBySubtractFilterOrderByLike(filter);

                Map<Long, List<RestaurantEnum.RestaurantCategoryType>> restaurantCategoryTypeMap = getRestaurantCategoryMap(
                        restaurants.stream()
                                .map(RestaurantSubtractByLikeVo::getRestaurantId)
                                .distinct()
                                .toList()
                );

                subtractRestaurantItems = restaurants.stream()
                        .map(restaurant -> SubtractRestaurantItem.of(restaurant, restaurantCategoryTypeMap.getOrDefault(restaurant.getRestaurantId(), new ArrayList<>())))
                        .toList();

            }
            case SAVED -> {
                List<RestaurantSubtractBySavedVo> restaurants = restaurantRepositoryImpl.findAllBySubtractFilterOrderBySaved(filter);

                Map<Long, List<RestaurantEnum.RestaurantCategoryType>> restaurantCategoryTypeMap = getRestaurantCategoryMap(
                        restaurants.stream()
                                .map(RestaurantSubtractBySavedVo::getRestaurantId)
                                .distinct()
                                .toList()
                );

                subtractRestaurantItems = restaurants.stream()
                        .map(restaurant -> SubtractRestaurantItem.of(restaurant, restaurantCategoryTypeMap.getOrDefault(restaurant.getRestaurantId(), new ArrayList<>())))
                        .toList();

            }
        }

        return SearchSubtractRestaurantsResponse.from(subtractRestaurantItems);
    }



    public SearchSubtractRestaurantLocationsResponse searchSubtractRestaurantLocations(SearchSubtractRestaurantLocationsRequest request) {


        if (request.userLocationItem() == null || request.userLocationItem().longitude() == null || request.userLocationItem().latitude() == null) {
            throw new IllegalArgumentException("사용자 위치를 읽을 수 없습니다.");
        }

        RestaurantSubtractFilter filter = RestaurantSubtractFilter.from(request);

        List<RestaurantSubtractByDistanceVo> restaurants = restaurantRepositoryImpl.findAllBySubtractFilterOrderByDistance(filter);

        Map<Long, List<RestaurantEnum.RestaurantCategoryType>> categoryMaps = getRestaurantCategoryMap(restaurants.stream()
                .map(RestaurantSubtractByDistanceVo::getRestaurantId).toList()
        );

        // 저장순으로 다시 진행
        List<SubtractRestaurantLocationItem> responseData = restaurants.stream()
                .map(restaurantSubtractByDistanceVo ->
                        SubtractRestaurantLocationItem.of(restaurantSubtractByDistanceVo,
                                categoryMaps.get(restaurantSubtractByDistanceVo.getRestaurantId())
                                        .stream()
                                        .findFirst()
                                        .orElse(null)))
                .toList();

        return SearchSubtractRestaurantLocationsResponse.from(responseData);
    }

    public GetSubtractRestaurantResponse getSubtractRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("식당 정보를 찾을 수 없습니다."));

        RestaurantMetadata restaurantMetadata = restaurantMetadataRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("식당 정보를 찾을 수 없습니다."));

        RestaurantOwnerMetadata restaurantOwnerMetadata = restaurantOwnerMetadataRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("식당 정보를 찾을 수 없습니다."));

        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findAllByRestaurant(restaurant)
                .stream().findFirst().orElseThrow(() -> new IllegalArgumentException("식당 정보를 찾을 수 없습니다."));

        SubtractRestaurantItem restaurantItem = SubtractRestaurantItem.builder()
                .restaurantId(restaurantId)
                .restaurantName(restaurant.getRestaurantName())
                .restaurantCategory(List.of(restaurantCategory.getRestaurantCategoryName()))
                .averagePrice(restaurantMetadata.getAveragePrice())
                .build();

        return GetSubtractRestaurantResponse.from(restaurantItem);
    }

    protected Map<Long, List<RestaurantEnum.RestaurantCategoryType>> getRestaurantCategoryMap(List<Long> restaurantIds) {
        List<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByRestaurants(restaurantIds);

        return restaurantCategories.stream()
                .collect(Collectors.groupingBy(
                        rc -> rc.getRestaurant().getRestaurantId(),
                        Collectors.mapping(
                                RestaurantCategory::getRestaurantCategoryName,
                                Collectors.toList()
                        )
                ));
    }

    protected List<ReviewEnum.ReviewTag> convertToReviewTag(RestaurantSubtractFilterItem filterItem) {

        Set<ReviewEnum.ReviewTag> reviewTags = new HashSet<>();

        // 2. 맛 필터
        if (!CollectionUtils.isEmpty(filterItem.favorFilterTypes())) {

            for (RestaurantEnum.FavorFilterType type : filterItem.favorFilterTypes()) {
                if (type == null) {
                    continue;
                }

                if (type.equals(TASTE_PLAIN)) {
                    // 전반적인 맛
                    reviewTags.add(ReviewEnum.ReviewTag.TASTE);
                } else if (type.equals(SIDE_PLAIN)) {
                    // 사이드 메뉴
                    reviewTags.add(ReviewEnum.ReviewTag.SIDE_MENU);
                } else if (type.equals(INGREDIENT_NOT_FRESH)) {
                    // 재료 신선도
                    reviewTags.add(ReviewEnum.ReviewTag.INGREDIENT);
                } else if (type.equals(MENU_LACKING)) {
                    // 메뉴 구성
                    reviewTags.add(ReviewEnum.ReviewTag.MENU);
                }
            }

        }


        // 3. 분위기 필터
        if (!CollectionUtils.isEmpty(filterItem.atmosphereFilterTypes())) {

            for (RestaurantEnum.AtmosphereFilterType type : filterItem.atmosphereFilterTypes()) {
                if (type == null) {
                    continue;
                }
                if (type.equals(ATMOSPHERE_LACKING)) {
                    reviewTags.add(ReviewEnum.ReviewTag.ATMOSPHERE);
                } else if (type.equals(NOISY)) {
                    reviewTags.add(ReviewEnum.ReviewTag.NOISE);
                }
            }
        }

        // 4. 화장실 필터
        if (!CollectionUtils.isEmpty(filterItem.toiletFilterTypes())) {

            for (RestaurantEnum.ToiletFilterType type : filterItem.toiletFilterTypes()) {
                if (type == null) {
                    continue;
                }

                if (type.equals(DIRTY)) {
                    reviewTags.add(ReviewEnum.ReviewTag.TOILET);
                    break;
                }
            }

        }

        // 5. 주차/서비스 필터
        if (!CollectionUtils.isEmpty(filterItem.serviceFilterTypes())) {
            for (RestaurantEnum.ServiceFilterType type : filterItem.serviceFilterTypes()) {
                if (type == null) {
                    continue;
                }

                if (type.equals(UNFRIENDLY)) {
                    reviewTags.add(ReviewEnum.ReviewTag.SERVICE);
                    break;
                }
            }
        }

        // 6. 기타 필터
        if (!CollectionUtils.isEmpty(filterItem.atcFilterTypes())) {
            for (RestaurantEnum.AtcFilterType type : filterItem.atcFilterTypes()) {
                if (type == null) {
                    continue;
                }

                if (type.equals(BAD_VALUE)) {
                    reviewTags.add(ReviewEnum.ReviewTag.COST);
                } else if (type.equals(SLOW_FOOD)) {
                    reviewTags.add(ReviewEnum.ReviewTag.TIME);
                } else if (type.equals(UNCOMFORTABLE_SEAT)) {
                    reviewTags.add(ReviewEnum.ReviewTag.SEAT);
                } else if (type.equals(UNHYGIENIC)) {
                    reviewTags.add(ReviewEnum.ReviewTag.HYGIENE);
                } else if (type.equals(LONG_WAITING)) {
                    reviewTags.add(WAITING_TIME);
                } else if (type.equals(NO_MEAT_GRILL_HELP)) {
                    reviewTags.add(MEAT_SERVICE);
                }
            }
        }

        return reviewTags.stream().toList();
    }

    private List<SubtractRestaurantItem> getSubtractRestaurantItemsByDistance(RestaurantSubtractFilter filter) {
        List<RestaurantSubtractByDistanceVo> restaurants = restaurantRepositoryImpl.findAllBySubtractFilterOrderByDistance(filter);

        Map<Long, List<RestaurantEnum.RestaurantCategoryType>> restaurantCategoryTypeMap = getRestaurantCategoryMap(
                restaurants.stream()
                        .map(RestaurantSubtractByDistanceVo::getRestaurantId)
                        .distinct()
                        .toList()
        );

        return restaurants.stream()
                .map(restaurant -> SubtractRestaurantItem.of(restaurant, restaurantCategoryTypeMap.getOrDefault(restaurant.getRestaurantId(), new ArrayList<>())))
                .toList();
    }
}
