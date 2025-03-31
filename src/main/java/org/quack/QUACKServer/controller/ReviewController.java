package org.quack.QUACKServer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.reviews.request.ReviewCreateRequest;
import org.quack.QUACKServer.dto.reviews.request.ReviewLikeUpdateRequest;
import org.quack.QUACKServer.dto.reviews.request.ReviewsSelectRequest;
import org.quack.QUACKServer.dto.reviews.response.ReviewLikeUpdateResponse;
import org.quack.QUACKServer.dto.reviews.response.ReviewsSelectResponse;
import org.quack.QUACKServer.service.ReviewService;
import org.quack.QUACKServer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;


    /**
     * 리뷰 작성 - 꽉찬 리뷰
     */
    @PostMapping("/review/full/{restaurant_id}")
    public ResponseEntity<Void> createFullReview(@NotNull(message = "restaurant_id 가 비어있으면 안됩니다.") @PathVariable("restaurant_id") Long restaurantId, Principal principal, @RequestBody ReviewCreateRequest request) {

        Long userId = Long.valueOf(principal.getName());

        reviewService.createReviews(restaurantId, userId, request);

        return ResponseEntity.status(CREATED).build();
    }


    /**
     * 리뷰 작성 - 간편 리뷰
     */
    @PostMapping("/review/simple/{restaurant_id}")
    public ResponseEntity<Void> createSimpleReview( @NotNull(message = "restaurant_id 가 비어있으면 안됩니다.") @PathVariable("restaurant_id") Long restaurantId, Principal principal, @RequestBody ReviewCreateRequest request) {

        Long userId = Long.valueOf(principal.getName());

        reviewService.createReviews(restaurantId, userId, request);

        return ResponseEntity.status(CREATED).build();
    }

    /**
     * 식당에 대한 리뷰 조회
     * TODO: 우선 개발 이후, Restaurant Controller 로 이관
     */
    @GetMapping("/restaurant/{restaurant_id}/reviews")
    public ResponseEntity<ReviewsSelectResponse> selectReviews(
            @NotNull(message = "restaurant_id 가 비어있으면 안됩니다.")
            @PathVariable("restaurant_id") Long restaurantId, Principal principal,
            @RequestBody ReviewsSelectRequest request) {

        Long userId = Long.valueOf(principal.getName());

        return ResponseEntity
                .ok(reviewService.selectReviews(restaurantId, userId, request));

    }

    /**
     * 리뷰 공감 수정
     */
    @PostMapping("/restaurant/review-like/update")
    public ResponseEntity<ReviewLikeUpdateResponse> updateReviewLikes(Principal principal,
                                                                      @Valid @RequestBody ReviewLikeUpdateRequest request) {
        Long userId = Long.valueOf(principal.getName());

        return ResponseEntity.status(CREATED).body(reviewService.updateReviewLikes(userId, request));
    }


    @PostMapping("/restaurant/{review_id}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable("review_id") Long reviewId) {

        reviewService.deleteReviewLike(reviewId);

        return ResponseEntity.status(CREATED).build();
    }


}
