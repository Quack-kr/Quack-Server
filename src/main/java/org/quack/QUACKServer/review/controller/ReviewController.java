package org.quack.QUACKServer.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.review.dto.response.MyReviewResponse;
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
    public String createReview(@PathVariable Long restaurantId,
                               @RequestBody CreateReviewRequest request) {
        CustomerUserInfo loginUser = getAuthenticatedUser();

        return reviewService.createReview(loginUser, restaurantId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long reviewId) {
        CustomerUserInfo loginUser = getAuthenticatedUser();

        return reviewService.deleteReview(loginUser, reviewId);
    }

    @GetMapping("/my-reviews")
    public MyReviewResponse getMyReviews(@RequestParam(defaultValue = "0") int pageNum,
                                         @RequestParam(defaultValue = "10") int sizeNum){
        CustomerUserInfo loginUser = getAuthenticatedUser();

        return reviewService.getMyReviews(loginUser, pageNum, sizeNum);
    }
}
