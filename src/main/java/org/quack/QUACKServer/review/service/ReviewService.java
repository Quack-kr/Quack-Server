package org.quack.QUACKServer.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.menu.service.MenuEvalService;
import org.quack.QUACKServer.menu.service.MenuService;
import org.quack.QUACKServer.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.photos.service.ReviewPhotoService;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.restaurant.service.RestaurantManager;
import org.quack.QUACKServer.review.domain.Review;
import org.quack.QUACKServer.review.domain.ReviewReport;
import org.quack.QUACKServer.review.dto.request.CreateReportRequest;
import org.quack.QUACKServer.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.review.dto.response.ReviewSimpleInfo;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.quack.QUACKServer.review.repository.ReviewReportRepository;
import org.quack.QUACKServer.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.quack.QUACKServer.core.error.constant.ErrorCode.DUPLICATE_REVIEW_REPORT;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordService reviewKeywordService;
    private final MenuEvalService menuEvalService;
    private final MenuService menuService;
    private final ReviewPhotoService reviewPhotoService;
    private final ReviewLikeService reviewLikeService;
    private final RestaurantManager restaurantManager;
    private final ReviewReportRepository reviewReportRepository;

    public ReviewInitResponse getInitData(Long restaurantId, String reviewType) {

        GetRestaurantInfoResponse restaurantInfo = restaurantManager.getRestaurantBasicInfo(restaurantId);

        if (reviewType.equals("FULL")) {
            Map<String, List<GetReviewMenusResponse>> menus = menuService.getMenusForReview(restaurantId);

            return ReviewInitResponse.of(restaurantInfo, menus);
        }

        return ReviewInitResponse.of(restaurantInfo, null);
    }

    @Transactional
    public ResponseDto<?> createReview(CustomerUserInfo customerUserInfo, Long restaurantId, CreateReviewRequest request) {

        boolean validationExistence = restaurantManager.validateExistence(restaurantId);
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
        reviewPhotoService.upload(reviewPhotoUploadRequest, customerUserInfo.getCustomerUserId());

        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<?> deleteReview(CustomerUserInfo customerUserInfo, Long reviewId){

        if(customerUserInfo == null) {
            throw new IllegalStateException("비로그인 시 리뷰 작성을 할 수 없습니다.");
        }

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

    @Transactional
    public String reportReview(CreateReportRequest request, Long customerUserId) {

        Review review = reviewRepository.findById(request.reviewId())
                .orElseThrow(() -> new CommonException(ErrorCode.REVIEW_NOT_FOUND));

        Optional<ReviewReport> reviewReport = reviewReportRepository
                .findByReviewIdAndRestaurantIdAndCustomerUserId(review.getReviewId(), review.getRestaurantId(), customerUserId);

        if (reviewReport.isPresent()) {
            throw new CommonException(DUPLICATE_REVIEW_REPORT);
        }

        ReviewReport newReviewReport = ReviewReport.create(review, request.reportContent(), customerUserId);

        reviewReportRepository.save(newReviewReport);

        return "성공";
    }


    private void validateDeletableReview(Long userId, Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (!Objects.equals(findReview.getUserId(), userId)) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");
        }
    }


}
