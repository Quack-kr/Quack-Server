package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    @Query("SELECT COALESCE(SUM(CASE " +
            "WHEN rl.likeType = '핵공감' THEN 1.0 " +
            "WHEN rl.likeType = '노공감' THEN -0.5 " +
            "ELSE 0 END), 0) " +
            "FROM ReviewLike rl " +
            "JOIN rl.review r " +
            "WHERE r.user.userId = :userId")
    Double aggregateEmpathyDecibelByUserId(Long userId);

}
