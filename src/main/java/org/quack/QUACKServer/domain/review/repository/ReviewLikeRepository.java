package org.quack.QUACKServer.domain.review.repository;

import org.quack.QUACKServer.domain.review.domain.ReviewLike;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.repository
 * @fileName : ReviewLikeRepository
 * @date : 25. 5. 31.
 */
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    @Query("SELECT COUNT(rl) FROM ReviewLike rl WHERE rl.userId = :customerUserId AND rl.likeType = :reviewLikeType")
    Long countReviewLikeByCustomerUserIdAndLikeType(@Param("customerUserId") Long customerUserId,
                                                    @Param("reviewLikeType") ReviewEnum.ReviewLikeType reviewLikeType);
}
