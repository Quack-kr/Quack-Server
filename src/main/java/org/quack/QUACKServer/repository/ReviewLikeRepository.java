package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.ReviewLike;
import org.quack.QUACKServer.domain.common.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    @Query("SELECT COALESCE(SUM(CASE " +
            "WHEN rl.likeType = 'HACK_GONG_GAM' THEN 1.0 " +
            "WHEN rl.likeType = '노공감' THEN -0.5 " +
            "ELSE 0 END), 0) " +
            "FROM ReviewLike rl " +
            "JOIN rl.review r " +
            "WHERE r.user.userId = :userId")
    Double aggregateEmpathyDecibelByUserId(Long userId);

    @Query("UPDATE ReviewLike rl SET rl.likeType = :likeType WHERE rl.user.userId = :userId AND rl.review.reviewId = :reviewId")
    void updateReviewLikeByUserIdAndReviewId(Long userId, Long reviewId, LikeType likeType);

    @Query("SELECT rl FROM ReviewLike rl WHERE rl.review.reviewId = :reviewId AND rl.user.userId = :userId" )
    Optional<ReviewLike> findByReviewIdAndUserId(Long reviewId, Long userId);

    @Query("SELECT rl FROM ReviewLike rl WHERE rl.review.reviewId = :reviewId")
    List<ReviewLike> findByReviewId(Long reviewId);
}

