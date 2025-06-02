package org.quack.QUACKServer.review.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.menu.service.MenuEvalService;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.review.dto.response.GetMyReviewResponse;
import org.quack.QUACKServer.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.review.dto.response.ReviewImageResponse;
import org.quack.QUACKServer.review.dto.response.ReviewInfoResponse;
import org.quack.QUACKServer.review.dto.response.ReviewWithRestaurantResponse;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.quack.QUACKServer.review.repository.ReviewLikeRepository;
import org.quack.QUACKServer.review.repository.ReviewRepository;
import org.quack.QUACKServer.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.user.service.CustomerUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.service
 * @fileName : ReviewMyPageService
 * @date : 25. 5. 27.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageReviewService {
    private final ReviewLikeRepository reviewLikeRepository;
    private final PhotosRepository photosRepository;
    private final CustomerUserService customerUserService;
    private final MenuEvalService menuEvalService;
    private final ReviewRepository reviewRepository;

    public GetReviewMyCountResponse getMyReviewCounts() {

        if(PrincipalManager.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        long count = reviewRepository.countByUserId(customerUserInfo.getCustomerUserId());

        return GetReviewMyCountResponse.from(count);

    }

    @Transactional
    public ResponseDto<?> deleteMyReview(Long reviewId) {

        reviewRepository.deleteById(reviewId);

        return ResponseDto.success("");

    }

    public ResponseDto<?> searchDecibel() {
        Long customerUserId = PrincipalManager.getCustomerUserId();

        Long reviewLike = reviewLikeRepository.countReviewLikeByCustomerUserIdAndLikeType(customerUserId, ReviewEnum.ReviewLikeType.LIKE);
        Long reviewDisLike = reviewLikeRepository.countReviewLikeByCustomerUserIdAndLikeType(customerUserId, ReviewEnum.ReviewLikeType.DISLIKE);

        Double decibel = (double) (Math.round(reviewLike - reviewDisLike * 0.5) / 100);

        return ResponseDto.successCreate(decibel);
    }

    public GetMyReviewResponse getMyReviews(int pageNum, int sizeNum) {

        if(PrincipalManager.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        Pageable pageable = PageRequest.of(pageNum, sizeNum);

        List<ReviewInfoResponse> reviewInfos = reviewRepository.findAllMyReview(customerUserInfo.getCustomerUserId(),
                pageable);
        List<ReviewWithRestaurantResponse> content = new ArrayList<>();


        for (ReviewInfoResponse reviewInfo : reviewInfos) {
            List<Photos> photosList = photosRepository.findAllByTargetIdAndPhotoType(
                    reviewInfo.reviewId(), PhotoType.REVIEW.name());

            List<ReviewImageResponse> reviewImageList = new ArrayList<>();

            if (!photosList.isEmpty()) {
                for (Photos photos : photosList) {
                    reviewImageList.add(ReviewImageResponse.from(photos.getImageUrl()));
                }
            }

            List<MenuEvalResponse> menuEvalList = menuEvalService.getMenuEvalsForReview(reviewInfo.reviewId());

            ReviewWithRestaurantResponse response = ReviewWithRestaurantResponse.of(
                    reviewInfo.restaurantName(),
                    reviewInfo.reviewCreatedAt(),
                    reviewInfo.reviewContent(),
                    reviewImageList,
                    menuEvalList,
                    reviewInfo.likeCount(),
                    reviewInfo.dislikeCount());

            content.add(response);
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(content.size() - 1);
        }

        SliceImpl<ReviewWithRestaurantResponse> allMyReview = new SliceImpl<>(content, pageable, hasNext);

        GetCustomerUserProfileResponse customerUserProfile = customerUserService.getCustomerUserProfile();
        Object userProfile = customerUserService.getCustomerUserProfilePhoto(
                customerUserProfile.profilePhotosId()).data();

        GetMyReviewResponse reviews = GetMyReviewResponse.of(allMyReview, customerUserProfile.nickname(),
                String.valueOf(userProfile));

        return reviews;
    }
}
