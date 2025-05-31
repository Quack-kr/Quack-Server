package org.quack.QUACKServer.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.domain.review.dto.response.MyReviewResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.domain.review.enums.ReviewType;
import org.quack.QUACKServer.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import static org.quack.QUACKServer.domain.auth.domain.QuackAuthContext.getAuthenticatedUser;

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
    public String createReview(@PathVariable Long restaurantId,
                               @RequestBody CreateReviewRequest request) {
        QuackUser loginUser = getAuthenticatedUser();

        return reviewService.createReview(loginUser, restaurantId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long reviewId) {
        QuackUser loginUser = getAuthenticatedUser();

        return reviewService.deleteReview(loginUser, reviewId);
    }

    @GetMapping("/my-reviews")
    public MyReviewResponse getMyReviews(@RequestParam(defaultValue = "0") int pageNum,
                                         @RequestParam(defaultValue = "10") int sizeNum){
        QuackUser loginUser = getAuthenticatedUser();

        return reviewService.getMyReviews(loginUser, pageNum, sizeNum);
    }
}
