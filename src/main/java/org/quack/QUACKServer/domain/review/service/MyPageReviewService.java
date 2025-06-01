package org.quack.QUACKServer.domain.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;
import org.quack.QUACKServer.domain.review.repository.ReviewLikeRepository;
import org.quack.QUACKServer.domain.review.repository.ReviewRepository;
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

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        long count = reviewRepository.countByUserId(quackUser.getCustomerUserId());

        return GetReviewMyCountResponse.from(count);

    }

    @Transactional
    public ResponseDto<?> deleteMyReview(Long reviewId) {

        reviewRepository.deleteById(reviewId);

        return ResponseDto.success("");

    }

    public ResponseDto<?> searchDecibel() {
        Long customerUserId = QuackAuthContext.getCustomerUserId();

        Long reviewLike = reviewLikeRepository.countReviewLikeByCustomerUserIdAndLikeType(customerUserId, ReviewEnum.ReviewLikeType.LIKE);
        Long reviewDisLike = reviewLikeRepository.countReviewLikeByCustomerUserIdAndLikeType(customerUserId, ReviewEnum.ReviewLikeType.DISLIKE);

        Double decibel = (double) (Math.round(reviewLike - reviewDisLike * 0.5) / 100);

        return ResponseDto.successCreate(decibel);
    }
}
