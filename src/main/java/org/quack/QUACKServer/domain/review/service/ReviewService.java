package org.quack.QUACKServer.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.service.MenuEvalService;
import org.quack.QUACKServer.domain.menu.service.MenuService;
import org.quack.QUACKServer.domain.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.domain.photos.service.ReviewPhotoService;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.domain.restaurant.service.RestaurantService;
import org.quack.QUACKServer.domain.review.domain.Review;
import org.quack.QUACKServer.domain.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        boolean validationExistence = restaurantService.validateExistence(restaurantId);
        if(!validationExistence) throw new IllegalArgumentException("식당 정보 없음.");

        GetRestaurantInfoResponse restaurantInfo = restaurantService.getRestaurantBasicInfo(restaurantId);

        if (reviewType.equals("FULL")) {
            List<GetReviewMenusResponse> menus = menuService.getMenusForReview(restaurantId);

            return ReviewInitResponse.of(restaurantInfo, menus);
        }

        return ReviewInitResponse.of(restaurantInfo, null);
    }

    @Transactional
    public String createReview(Long restaurantId, CreateReviewRequest request) {
        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 리뷰 작성을 할 수 없습니다.");
        }

        QuackUser user = QuackAuthContext.getQuackUserDetails();

        boolean validationExistence = restaurantService.validateExistence(restaurantId);
        if(!validationExistence) throw new IllegalArgumentException("식당 정보 없음.");

        Review review = Review.createReview(user.getCustomerUserId(), restaurantId, request.content());

        Review savedReview = reviewRepository.save(review);
        Long reviewId = savedReview.getReviewId();

        if (request.negativeKeywords() != null) {
            reviewKeywordService.saveReviewKeyword(reviewId, request.negativeKeywords(), ReviewKeywordType.NEGATIVE);
        }

        if (request.positiveKeywords() != null) {
            reviewKeywordService.saveReviewKeyword(reviewId, request.positiveKeywords(), ReviewKeywordType.POSITIVE);
        }


        if (request.menusEval() != null) {
            menuEvalService.saveMenusEval(reviewId, request.menusEval());
        }

        ReviewPhotoUploadRequest reviewPhotoUploadRequest = ReviewPhotoUploadRequest.of(
                reviewId,
                request.reviewPhotos()
        );
        reviewPhotoService.upload(reviewPhotoUploadRequest);

        return "Success";
    }

    @Transactional
    public String deleteReview(Long reviewId){

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 리뷰를 삭제할 수 없습니다.");
        }

        QuackUser user = QuackAuthContext.getQuackUserDetails();


        validateDeletableReview(user.getCustomerUserId(), reviewId);

        reviewRepository.deleteById(reviewId);
        menuEvalService.menuEvalDelete(reviewId);
        reviewLikeService.reviewLikeDelete(reviewId);
        reviewPhotoService.delete(reviewId);

        return "Success";
    }


    private void validateDeletableReview(Long userId, Long reviewId){
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (findReview.getUserId() != userId) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");
        }
    }


}
