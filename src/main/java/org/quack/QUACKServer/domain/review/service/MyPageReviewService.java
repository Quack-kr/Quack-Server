package org.quack.QUACKServer.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.domain.review.repository.ReviewRepository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
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

    public GetReviewMyCountResponse getMyReviewCounts() {

        if(QuackAuthContext.isAnonymous()) {
            throw new IllegalStateException("비로그인 시 조회 할 수 없습니다.");
        }

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        long count = reviewRepository.countByUserId(quackUser.getCustomerUserId());

        return GetReviewMyCountResponse.from(count);

    }

    public CommonResponse deleteMyReview(Long reviewId) {
        // QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        reviewRepository.deleteById(reviewId);

        return CommonResponse.of("201", "리뷰 삭제가 완료되었습니다.", HttpStatus.CREATED, "");

    }
}
