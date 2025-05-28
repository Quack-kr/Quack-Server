package org.quack.QUACKServer.domain.review.repository;

import org.quack.QUACKServer.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review
 * @fileName : ReviewRepository
 * @date : 25. 5. 27.
 */
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom{


    @Query("SELECT COUNT (r) FROM Review r WHERE r.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);

}
