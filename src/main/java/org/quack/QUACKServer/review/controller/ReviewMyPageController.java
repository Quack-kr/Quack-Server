package org.quack.QUACKServer.review.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.review.service.MyPageReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.controller
 * @fileName : ReviewMyPageController
 * @date : 25. 5. 27.
 */
@RestController
@Slf4j
@RequestMapping(path = "")
@RequiredArgsConstructor
public class ReviewMyPageController {

    private final MyPageReviewService myPageReviewService;

    /**
     * 마이페이지 - 저장된 리뷰수 조회
     */
    @GetMapping("/my-page/review-count")
    public GetReviewMyCountResponse selectMyPageReviewCount(
            @AuthenticationPrincipal CustomerUserInfo customerUserInfo) {

        if(customerUserInfo == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }

        return myPageReviewService.getMyReviewCounts(customerUserInfo.getCustomerUserId());
    }


    /**
     *
     * 마이페이지 - 리뷰 삭제
     */
    @PostMapping("/my-page/delete-review")
    public ResponseDto<?> deleteMyPageReview(@Valid @NotNull @RequestBody Long reviewId ) {
        return myPageReviewService.deleteMyReview(reviewId);
    }


    /**
     *
     * 마이페이지 - 핵공감 데시벨 조회
     */
    @PostMapping("/my-page/decibel")
    public ResponseDto<?> deleteMyPageReview(@AuthenticationPrincipal CustomerUserInfo customerUserInfo) {
        if(customerUserInfo == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }

        return myPageReviewService.searchDecibel(customerUserInfo.getCustomerUserId());
    }
}
