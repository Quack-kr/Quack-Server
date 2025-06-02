package org.quack.QUACKServer.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.review.enums.ReviewType;
import org.quack.QUACKServer.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import static org.quack.QUACKServer.auth.domain.PrincipalManager.getAuthenticatedUser;

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
    public ResponseDto<?> createReview(@PathVariable Long restaurantId,
                                    @RequestBody CreateReviewRequest request) {

        return reviewService.createReview(restaurantId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public ResponseDto<?> deleteReview(@PathVariable Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }

}
