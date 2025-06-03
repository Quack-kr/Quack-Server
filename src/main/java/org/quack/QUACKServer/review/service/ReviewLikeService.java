package org.quack.QUACKServer.review.service;


import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.review.repository.ReviewLikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {
    private final ReviewLikeRepository reviewLikeRepository;

    public boolean reviewLikeDelete(Long reviewId) {
        return reviewLikeRepository.deleteReviewLikeByReviewId(reviewId);
    }
}
