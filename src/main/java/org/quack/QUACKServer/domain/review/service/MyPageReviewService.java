package org.quack.QUACKServer.domain.review.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.domain.menu.service.MenuEvalService;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.domain.review.dto.response.GetMyReviewResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewImageResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInfoResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewWithRestaurantResponse;
import org.quack.QUACKServer.domain.review.repository.ReviewRepository;
import org.quack.QUACKServer.domain.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.domain.user.service.CustomerUserService;
import org.springframework.core.io.Resource;
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

    private final ReviewRepository reviewRepository;
    private final PhotosRepository photosRepository;
    private final CustomerUserService customerUserService;
    private final MenuEvalService menuEvalService;

    public GetReviewMyCountResponse getMyReviewCounts() {

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        long count = reviewRepository.countByUserId(quackUser.getCustomerUserId());

        return GetReviewMyCountResponse.from(count);

    }


    public GetMyReviewResponse getMyReviews(int pageNum, int sizeNum) {

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        Pageable pageable = PageRequest.of(pageNum, sizeNum);

        List<ReviewInfoResponse> reviewInfos = reviewRepository.findAllMyReview(quackUser.getCustomerUserId(), pageable);
        List<ReviewWithRestaurantResponse> content = new ArrayList<>();


        for (ReviewInfoResponse reviewInfo : reviewInfos) {
            List<Photos> photosList = photosRepository.findAllByTargetIdAndPhotoType(
                    reviewInfo.getReviewId(), PhotoType.REVIEW.name());

            List<ReviewImageResponse> reviewImageList = new ArrayList<>();

            for (Photos photos : photosList) {
                reviewImageList.add(ReviewImageResponse.from(photos.getImageUrl()));
            }

            List<MenuEvalResponse> menuEvalList = menuEvalService.getMenuEvalsForReview(reviewInfo.reviewId());

            ReviewWithRestaurantResponse response = ReviewWithRestaurantResponse.of(
                    reviewInfo.getRestaurantName(),
                    reviewInfo.getReviewCreatedAt(),
                    reviewInfo.getReviewContent(),
                    reviewImageList,
                    menuEvalList,
                    reviewInfo.getLikeCount(),
                    reviewInfo.getDislikeCount());

            content.add(response);
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(content.size() - 1);
        }

        SliceImpl<ReviewWithRestaurantResponse> allMyReview = new SliceImpl<>(content, pageable, hasNext);

        GetCustomerUserProfileResponse customerUserProfile = customerUserService.getCustomerUserProfile();
        Resource userProfile = customerUserService.getCustomerUserProfilePhoto(
                customerUserProfile.profileImageId());

        GetMyReviewResponse reviews = GetMyReviewResponse.of(allMyReview, customerUserProfile.nickname(), userProfile);
        
        return reviews;
    }
}
