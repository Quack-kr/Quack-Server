package org.quack.QUACKServer.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
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
    public String createReview(CustomerUserInfo user, Long restaurantId, CreateReviewRequest request) {
        checkLoginUser(user);

        boolean validationExistence = restaurantService.validateExistence(restaurantId);
        if(!validationExistence) throw new IllegalArgumentException("식당 정보 없음.");

        Review review = Review.createReview(user.getCustomerUserId(), restaurantId, request.content());

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
        reviewPhotoService.upload(reviewPhotoUploadRequest, user.getCustomerUserId());

        return "Success";
    }

    @Transactional
    public String deleteReview(CustomerUserInfo user, Long reviewId){
        checkLoginUser(user);

        validateDeletableReview(user.getCustomerUserId(), reviewId);

        reviewRepository.deleteById(reviewId);
        menuEvalService.menuEvalDelete(reviewId);
        // ReviewLike 삭제, ReviewPhoto 삭제 구현

        return "Success";
    }

    public MyReviewResponse getMyReviews(CustomerUserInfo user, int pageNum, int sizeNum) {
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

            ReviewWithRestaurantResponse response = ReviewWithRestaurantResponse.of(
                    reviewInfo.reviewId(),
                    reviewInfo.restaurantName(),
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

        GetCustomerUserProfileResponse customerUserProfile = customerUserService.getCustomerUserProfile(user.getCustomerUserId());

        return MyReviewResponse.of(allMyReview, customerUserProfile.nickname(),
                customerUserProfile.profilePhotosId());
    }


    private void checkLoginUser(CustomerUserInfo user) {
        if (user == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
    }

    private void validateDeletableReview(Long userId, Long reviewId){
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (!Objects.equals(findReview.getUserId(), userId)) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");
        }
    }


}
