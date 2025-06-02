package org.quack.QUACKServer.review.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.menu.service.MenuEvalService;
import org.quack.QUACKServer.menu.service.MenuService;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.photos.service.ReviewPhotoService;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.restaurant.service.RestaurantService;
import org.quack.QUACKServer.review.domain.Review;
import org.quack.QUACKServer.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.review.dto.response.*;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.quack.QUACKServer.review.repository.ReviewRepository;
import org.quack.QUACKServer.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.user.service.CustomerUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordService reviewKeywordService;
    private final MenuEvalService menuEvalService;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final ReviewPhotoService reviewPhotoService;
    private final ReviewLikeService reviewLikeService;

    public ReviewInitResponse getInitData(Long restaurantId, String reviewType) {

        GetRestaurantInfoResponse restaurantInfo = restaurantService.getRestaurantBasicInfo(restaurantId);

        if (reviewType.equals("FULL")) {
            Map<String, List<GetReviewMenusResponse>> menus = menuService.getMenusForReview(restaurantId);

            return ReviewInitResponse.of(restaurantInfo, menus);
        }

        return ReviewInitResponse.of(restaurantInfo, null);
    }

    @Transactional
    public ResponseDto<?> createReview(Long restaurantId, CreateReviewRequest request) {

        if(PrincipalManager.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 리뷰 작성을 할 수 없습니다.");
        }

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        boolean validationExistence = restaurantService.validateExistence(restaurantId);
        if(!validationExistence) throw new IllegalArgumentException("식당 정보 없음.");

        Review review = Review.createReview(customerUserInfo.getCustomerUserId(), restaurantId, request.content());

        Review savedReview = reviewRepository.save(review);
        Long reviewId = savedReview.getReviewId();

        if (request.negativeKeywords() != null) {
            reviewKeywordService.saveReviewKeyword(reviewId, request.negativeKeywords(), ReviewEnum.ReviewKeywordType.NEGATIVE);
        }

        if (request.positiveKeywords() != null) {
            reviewKeywordService.saveReviewKeyword(reviewId, request.positiveKeywords(), ReviewEnum.ReviewKeywordType.POSITIVE);
        }


        if (request.menusEval() != null) {
            menuEvalService.saveMenusEval(reviewId, request.menusEval());
        }

        ReviewPhotoUploadRequest reviewPhotoUploadRequest = ReviewPhotoUploadRequest.of(
                reviewId,
                request.reviewPhotos()
        );
        reviewPhotoService.upload(reviewPhotoUploadRequest);

        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<?> deleteReview(Long reviewId){

        if(PrincipalManager.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 리뷰 작성을 할 수 없습니다.");
        }

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        validateDeletableReview(customerUserInfo.getCustomerUserId(), reviewId);

        reviewRepository.deleteById(reviewId);
        menuEvalService.menuEvalDelete(reviewId);
        reviewLikeService.reviewLikeDelete(reviewId);
        reviewPhotoService.delete(reviewId);

        return ResponseDto.success(null);
    }


    public Long getRestaurantReviewTotalCount(Long restaurantId) {
        return reviewRepository.countByRestaurantId(restaurantId);
    }

    public List<ReviewSimpleInfo> getRestaurantRecentReview(Long restaurantId) {
        return reviewRepository.findByRestaurantRecentReview(restaurantId);
    }


    private void validateDeletableReview(Long userId, Long reviewId){
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (!Objects.equals(findReview.getUserId(), userId)) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");
        }
    }


}
