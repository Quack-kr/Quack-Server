package org.quack.QUACKServer.domain.review.controller;

import static org.quack.QUACKServer.domain.auth.domain.QuackAuthContext.getAuthenticatedUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.review.dto.request.CreateReviewRequest;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInitResponse;
import org.quack.QUACKServer.domain.review.enums.ReviewType;
import org.quack.QUACKServer.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/review")
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

        return reviewService.createReview(restaurantId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }

}
