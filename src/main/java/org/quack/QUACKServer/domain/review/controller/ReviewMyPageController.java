package org.quack.QUACKServer.domain.review.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.domain.review.service.MyPageReviewService;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
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
    public GetReviewMyCountResponse selectMyPageReviewCount() {
        return myPageReviewService.getMyReviewCounts();
    }


    /**
     *
     * 마이페이지 - 리뷰 삭제
     */
    @PostMapping("/my-page/delete-review")
    public CommonResponse deleteMyPageReview(@Valid @NotNull @RequestBody Long reviewId ) {
        return myPageReviewService.deleteMyReview(reviewId);
    }


    /**
     *
     * 마이페이지 - 핵공감 데시벨 조회
     */
    @PostMapping("/my-page/decibel")
    public CommonResponse deleteMyPageReview() {
        return myPageReviewService.searchDecibel();
    }
}
