package org.quack.QUACKServer.domain.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.BestMenu;
import org.quack.QUACKServer.domain.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.domain.menu.service.MenuEvalService;
import org.quack.QUACKServer.domain.menu.service.MenuService;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.domain.restaurant.dto.response.DayOperatingInfo;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantDetailInfo;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.RestaurantInfo;
import org.quack.QUACKServer.domain.restaurant.repository.RestaurantRepository;
import org.quack.QUACKServer.domain.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.domain.review.dto.response.ReviewSimpleInfo;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.domain.review.service.ReviewEvalSummaryService;
import org.quack.QUACKServer.domain.review.service.ReviewService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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

        Resource restaurantRepresentativePhoto = getRestaurantRepresentativePhoto(restaurantId);

        return GetRestaurantInfoResponse.of(
                findRestaurant.getRestaurantName(),
                findRestaurant.getDetailAddress(),
                restaurantCategory,
                restaurantRepresentativePhoto
        );
    }

    public Resource getRestaurantRepresentativePhoto(Long restaurantId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(restaurantId, PhotoEnum.PhotoType.RESTAURANT.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return photosS3Repository.get(PhotosFileDto.builder().keyName(photos.getImageUrl()).build());


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
