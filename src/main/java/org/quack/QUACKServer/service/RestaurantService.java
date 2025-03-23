package org.quack.QUACKServer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.common.KeywordType;
import org.quack.QUACKServer.dto.restaurant.MenuDto;
import org.quack.QUACKServer.dto.restaurant.OperatingInfoDto;
import org.quack.QUACKServer.dto.restaurant.RecentReviewDto;
import org.quack.QUACKServer.dto.restaurant.RestaurantBasicInfoDto;
import org.quack.QUACKServer.dto.restaurant.RestaurantFeatureDto;
import org.quack.QUACKServer.dto.restaurant.RestaurantInfoResponse;
import org.quack.QUACKServer.dto.restaurant.ReviewKeywordDto;
import org.quack.QUACKServer.repository.RestaurantRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final ReviewService reviewService;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantOperatingService restaurantOperatingService;
    private final RestaurantFeatureService restaurantFeatureService;
    private final MenuService menuService;
    private final ReviewKeywordService reviewKeywordService;
    private final SavedRestaurantService savedRestaurantService;

    public RestaurantInfoResponse getRestaurantInfo(Long restaurantId, Long userId) {
        checkExistingRestaurant(restaurantId);

        // Restaurant basic info
        RestaurantBasicInfoDto basicInfo = getRestaurantBasicInfoDto(restaurantId);

        // Restaurant operating info
        List<OperatingInfoDto> operatingInfo = restaurantOperatingService.getOperatingInfo(restaurantId);

        // Restaurant feature info
        List<RestaurantFeatureDto> featureInfo = restaurantFeatureService.getFeatureInfo(restaurantId);

        // Restaurant Menus
        List<MenuDto> menus = menuService.getMenus(restaurantId);

        Pageable pageable = PageRequest.of(0, 3);
        // Restaurant positive keyword evaluation
        List<ReviewKeywordDto> top3PositiveEvaluation = reviewKeywordService.getReviewKeyword(restaurantId,
                KeywordType.POSITIVE, pageable);

        // Restaurant negative keyword evaluation
        List<ReviewKeywordDto> top3NegativeEvaluation = reviewKeywordService.getReviewKeyword(restaurantId,
                KeywordType.NEGATIVE, pageable);

        // Recent Review
        List<RecentReviewDto> recentReview = reviewService.getRecentReviewByRestaurantId(restaurantId);

        // 사용자가 저장했는지
        boolean isSaved = savedRestaurantService.isRestaurantSaved(userId, restaurantId);

        // 영업중인지
        boolean isOpen = restaurantOperatingService.isRestaurantOpen(restaurantId);


        RestaurantInfoResponse restaurantInfoResponse = RestaurantInfoResponse.builder()
                .restaurantBasicInfoDto(basicInfo)
                .operatingInfo(operatingInfo)
                .features(featureInfo)
                .menus(menus)
                .top3PositiveKeywords(top3PositiveEvaluation)
                .top3NegativeKeywords(top3NegativeEvaluation)
                .top3RecentReviews(recentReview)
                .isSaved(isSaved)
                .isOpen(isOpen)
                .build();

        return restaurantInfoResponse;
    }

    private void checkExistingRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException("해당 식당이 존재하지 않습니다. restaurantId: " + restaurantId);
        }
    }


    private RestaurantBasicInfoDto getRestaurantBasicInfoDto(Long restaurantId) {
        RestaurantBasicInfoDto basicInfo = restaurantRepository.findBasicInfoByRestaurantId(restaurantId);
        return basicInfo;
    }

}
