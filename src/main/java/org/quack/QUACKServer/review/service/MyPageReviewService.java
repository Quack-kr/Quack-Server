package org.quack.QUACKServer.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.quack.QUACKServer.review.repository.ReviewLikeRepository;
import org.quack.QUACKServer.review.repository.ReviewRepository;
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
}
