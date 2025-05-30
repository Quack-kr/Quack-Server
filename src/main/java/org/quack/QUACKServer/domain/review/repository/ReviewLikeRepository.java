package org.quack.QUACKServer.domain.review.repository;

import org.quack.QUACKServer.domain.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean deleteReviewLikeByReviewId(Long reviewId);
}
