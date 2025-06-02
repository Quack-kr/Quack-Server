package org.quack.QUACKServer.restaurant.service;

import static org.quack.QUACKServer.restaurant.domain.QRestaurant.restaurant;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.menu.dto.response.BestMenu;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.menu.service.MenuEvalService;
import org.quack.QUACKServer.menu.service.MenuService;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.photos.enums.PhotoEnum;
import org.quack.QUACKServer.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.restaurant.domain.CustomerSavedRestaurant;
import org.quack.QUACKServer.restaurant.domain.Restaurant;
import org.quack.QUACKServer.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.restaurant.dto.response.DayOperatingInfo;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantDetailInfo;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.restaurant.dto.response.RestaurantInfo;
import org.quack.QUACKServer.restaurant.repository.CustomerSavedRestaurantRepository;
import org.quack.QUACKServer.restaurant.repository.RestaurantRepository;
import org.quack.QUACKServer.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.review.dto.response.ReviewSimpleInfo;
import org.quack.QUACKServer.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.review.service.ReviewEvalSummaryService;
import org.quack.QUACKServer.review.service.ReviewService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    public GetRestaurantInfoResponse getRestaurantBasicInfo(Long restaurantId){
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("식당 정보 없음"));

        List<RestaurantCategory> restaurantCategories = findRestaurant.getRestaurantCategories();

        List<String> restaurantCategory = new ArrayList<>();

        if(!restaurantCategories.isEmpty()){
            for (RestaurantCategory category : restaurantCategories) {
                restaurantCategory.add(category.getRestaurantCategoryName().getDescription());
            }
        }

        String restaurantRepresentativePhoto = getRestaurantRepresentativePhoto(restaurantId);

        return GetRestaurantInfoResponse.of(
                findRestaurant.getRestaurantName(),
                findRestaurant.getDetailAddress(),
                restaurantCategory,
                restaurantRepresentativePhoto
        );
    }

    @Transactional
    public ResponseDto<?> updateCustomerUserRestaurant(Long restaurantId) {
        Long customerUserId = PrincipalManager.getCustomerUserId();

        Optional<CustomerSavedRestaurant> customerSavedRestaurant = customerSavedRestaurantRepository
                .findByCustomerUserIdAndRestaurantId(customerUserId,restaurantId);

        if(customerSavedRestaurant.isPresent()){
            customerSavedRestaurantRepository.delete(customerSavedRestaurant.get());
        } else {
            customerSavedRestaurantRepository.save(CustomerSavedRestaurant.create(customerUserId, restaurantId));
        }

        return ResponseDto.successCreate(null);

    }
    public String getRestaurantRepresentativePhoto(Long restaurantId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(restaurantId, PhotoEnum.PhotoType.RESTAURANT.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return photos.getImageUrl();


    }

    public GetRestaurantDetailInfo getRestaurantDetailInfo(Long restaurantId) {

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

        boolean isSaved = myPageRestaurantService.checkSavedRestaurant(restaurantId);


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



    public boolean validateExistence(Long restaurantId){
        return restaurantRepository.existsById(restaurantId);
    }


}
