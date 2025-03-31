package org.quack.QUACKServer.repository.review;

import java.util.List;
import org.quack.QUACKServer.domain.Review;
import org.springframework.data.domain.Page;
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

    @Query("select r from Review r where r.restaurant.restaurantId = :restaurantId")
    List<Review> findAllByRestaurantId(Long restaurantId);


    @Query("select r from Review r where r.restaurant.restaurantId = :restaruantId")
    List<Review> findAllByRestaurantId(Long restaurantId , Pageable pageable);

    @Query("""
    SELECT r FROM Review r 
    LEFT JOIN r.reviewLike rl 
    WHERE rl.likeType = 'HACK_GONG_GAM'
    AND r.restaurant.restaurantId = :restaurantId
    GROUP BY r 
    ORDER BY COUNT(rl) DESC
    """)
    List<Review> findAllOrderByHackGongGamCountDesc(Long restaurantId, Pageable pageable);
}
