package org.quack.QUACKServer.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.menu.dto.response.BestMenu;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.menu.service.MenuEvalService;
import org.quack.QUACKServer.menu.service.MenuService;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.restaurant.domain.CustomerSavedRestaurant;
import org.quack.QUACKServer.restaurant.dto.response.DayOperatingInfo;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantDetailInfo;
import org.quack.QUACKServer.restaurant.dto.response.RestaurantInfo;
import org.quack.QUACKServer.restaurant.repository.CustomerSavedRestaurantRepository;
import org.quack.QUACKServer.restaurant.repository.RestaurantRepository;
import org.quack.QUACKServer.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.review.dto.response.ReviewSimpleInfo;
import org.quack.QUACKServer.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.review.service.ReviewEvalSummaryService;
import org.quack.QUACKServer.review.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MyPageRestaurantService myPageRestaurantService;
    private final ReviewEvalSummaryService reviewEvalSummaryService;
    private final ReviewService reviewService;
    private final MenuService menuService;
    private final MenuEvalService menuEvalService;
    private final PhotosRepository photosRepository;
    private final PhotosS3Repository photosS3Repository;
    private final CustomerSavedRestaurantRepository customerSavedRestaurantRepository;


    @Transactional
    public ResponseDto<?> updateCustomerUserRestaurant(Long restaurantId, Long customerUserId) {

        Optional<CustomerSavedRestaurant> customerSavedRestaurant = customerSavedRestaurantRepository
                .findByCustomerUserIdAndRestaurantId(customerUserId,restaurantId);

        if(customerSavedRestaurant.isPresent()){
            customerSavedRestaurantRepository.delete(customerSavedRestaurant.get());
        } else {
            customerSavedRestaurantRepository.save(CustomerSavedRestaurant.create(customerUserId, restaurantId));
        }

        return ResponseDto.successCreate(null);

    }

    public GetRestaurantDetailInfo getRestaurantDetailInfo(CustomerUserInfo customerUserInfo, Long restaurantId) {

        RestaurantInfo restaurantInfo = restaurantRepository.findRestaurantInfoByRestaurantId(restaurantId);

        List<Photos> restaurantPhotos = photosRepository.findAllByTargetIdAndPhotoType(restaurantId,
                PhotoType.RESTAURANT.name());
        List<String> restaurantImages = new ArrayList<>();

        if (!restaurantPhotos.isEmpty()) {
            for (Photos photo : restaurantPhotos) {
                restaurantImages.add(photo.getImageUrl());
            }
        }

        Map<String, DayOperatingInfo> restaurantOperationInfo = restaurantRepository.findOperationInfo(restaurantId);

        List<ReviewEvalSummaryRank> negativeKeywords = reviewEvalSummaryService.getReviewEvalSummary(restaurantId,
                ReviewKeywordType.NEGATIVE);

        List<ReviewEvalSummaryRank> positiveKeywords = reviewEvalSummaryService.getReviewEvalSummary(restaurantId,
                ReviewKeywordType.POSITIVE);

        List<BestMenu> bestMenuList = menuEvalService.getRestaurantBestMenu(restaurantId);

        List<MenuSimpleInfo> menuInfoList = menuService.getMenuSimpleInfo(restaurantId);

        Long reviewCount = reviewService.getRestaurantReviewTotalCount(restaurantId);

        List<ReviewSimpleInfo> top3Review = reviewService.getRestaurantRecentReview(restaurantId);

        boolean isSaved = myPageRestaurantService.checkSavedRestaurant(customerUserInfo, restaurantId);


        return GetRestaurantDetailInfo.of(
                restaurantInfo,
                restaurantImages,
                restaurantOperationInfo,
                negativeKeywords,
                positiveKeywords,
                bestMenuList,
                menuInfoList,
                reviewCount,
                top3Review,
                isSaved

        );

    }

}
