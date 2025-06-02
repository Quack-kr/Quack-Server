package org.quack.QUACKServer.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.review.dto.response.MyReviewResponse;
import org.quack.QUACKServer.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.review.enums.ReviewType;
import org.quack.QUACKServer.review.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{restaurantId}/init")
    public ReviewInitResponse init(@PathVariable Long restaurantId, @RequestParam("type") ReviewType reviewType) {
        return reviewService.getInitData(restaurantId, reviewType.getValue());
    }

    @PostMapping("/{restaurantId}/create")
    public String createReview(
            @AuthenticationPrincipal CustomerUserInfo loginUser,
            @PathVariable Long restaurantId, @RequestBody CreateReviewRequest request) {

        if(loginUser == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }
        return reviewService.createReview(loginUser, restaurantId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(
            @AuthenticationPrincipal CustomerUserInfo loginUser,
            @PathVariable Long reviewId) {
        if(loginUser == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }
        return reviewService.deleteReview(loginUser, reviewId);
    }

    @GetMapping("/my-reviews")
    public MyReviewResponse getMyReviews(
            @AuthenticationPrincipal CustomerUserInfo loginUser,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int sizeNum){
        if(loginUser == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }
        return reviewService.getMyReviews(loginUser, pageNum, sizeNum);
    }
}
