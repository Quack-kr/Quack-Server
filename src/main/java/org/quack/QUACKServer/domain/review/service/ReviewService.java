package org.quack.QUACKServer.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.domain.menu.service.MenuEvalService;
import org.quack.QUACKServer.domain.menu.service.MenuService;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.service.ReviewPhotoService;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.domain.restaurant.service.RestaurantService;
import org.quack.QUACKServer.domain.review.domain.Review;
import org.quack.QUACKServer.domain.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.domain.review.dto.response.*;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.domain.review.repository.ReviewRepository;
import org.quack.QUACKServer.domain.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.domain.user.service.CustomerUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private final PhotosRepository photosRepository;
    private final CustomerUserService customerUserService;

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
    public String createReview(QuackUser user,  Long restaurantId, CreateReviewRequest request) {
        checkLoginUser(user);

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
    public String deleteReview(QuackUser user, Long reviewId){
        checkLoginUser(user);

        validateDeletableReview(user.getCustomerUserId(), reviewId);

        reviewRepository.deleteById(reviewId);
        menuEvalService.menuEvalDelete(reviewId);
        // ReviewLike 삭제, ReviewPhoto 삭제 구현

        return "Success";
    }

    public MyReviewResponse getMyReviews(QuackUser user, int pageNum, int sizeNum) {
        checkLoginUser(user);

        Pageable pageable = PageRequest.of(pageNum, sizeNum);

        List<ReviewInfoResponse> reviewInfos = reviewRepository.findAllMyReview(user.getCustomerUserId(), pageable);
        List<ReviewWithRestaurantResponse> content = new ArrayList<>();


        for (ReviewInfoResponse reviewInfo : reviewInfos) {
            List<Photos> photosList = photosRepository.findAllByTargetIdAndPhotoType(
                    reviewInfo.reviewId(), PhotoType.REVIEW.name());

            List<ReviewImageResponse> reviewImageList = new ArrayList<>();

            for (Photos photos : photosList) {
                reviewImageList.add(ReviewImageResponse.from(photos.getImageUrl()));
            }

            List<MenuEvalResponse> menuEvalList = menuEvalService.getMenuEvalsForReview(reviewInfo.reviewId());

            ReviewWithRestaurantResponse response = ReviewWithRestaurantResponse.of(reviewInfo.restaurantName(),
                    reviewInfo.reviewCreatedAt(),
                    reviewInfo.reviewContent(), reviewImageList, menuEvalList, reviewInfo.likeCount(),
                    reviewInfo.dislikeCount());

            content.add(response);
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.removeLast();
        }

        SliceImpl<ReviewWithRestaurantResponse> allMyReview = new SliceImpl<>(content, pageable, hasNext);

        GetCustomerUserProfileResponse customerUserProfile = customerUserService.getCustomerUserProfile();

        return MyReviewResponse.of(allMyReview, customerUserProfile.nickname(),
                customerUserProfile.profileImageId());
    }


    private void checkLoginUser(QuackUser user) {
        if (user == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
    }

    private void validateDeletableReview(Long userId, Long reviewId){
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (findReview.getUserId() != userId) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");
        }
    }


}
