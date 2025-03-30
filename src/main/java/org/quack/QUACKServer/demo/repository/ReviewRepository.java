package org.quack.QUACKServer.demo.repository;

import java.util.List;

import org.quack.QUACKServer.demo.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    int countByUser_UserId(Long userId);

    @Query("SELECT r " +
            "FROM Review r " +
            "WHERE r.restaurant.restaurantId = :restaurantId " +
            "  AND LENGTH(r.content) >= :minLength " +
            "ORDER BY r.createdDate DESC")
    List<Review> findRecentReviewsByRestaurantId(Long restaurantId, int minLength, Pageable pageable);

    List<Review> findAllByUser_UserId(Long userId);
}
