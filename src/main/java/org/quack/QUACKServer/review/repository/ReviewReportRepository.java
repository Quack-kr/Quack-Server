package org.quack.QUACKServer.review.repository;

import org.quack.QUACKServer.review.domain.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.review.repository
 * @fileName : ReviewReportRepository
 * @date : 25. 6. 2.
 */
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {

    Optional<ReviewReport> findByReviewIdAndRestaurantIdAndCustomerUserId (@Param("reviewId") Long reviewId, @Param("restaurantId") Long restaurantId, @Param("customerUserId") Long customerUserId);
}
