package org.quack.QUACKServer.repository;


import org.quack.QUACKServer.domain.SavedRestaurant;
import org.quack.QUACKServer.dto.restaurant.SavedRestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    int getCountByUser_UserId(Long userId);

    boolean existsByUser_UserIdAndRestaurant_RestaurantId(Long userId, Long restaurantId);


    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.SavedRestaurantDto(" +
            "       r.restaurantId, " +
            "       r.representativeImage, " +
            "       r.restaurantName, " +
            "       r.category, " +
            "       r.simpleDescription, " +
            "       0L, " +
            "       function('round', AVG(m.price)), " +
            "       false, " +
            "       r.latitude, " +
            "       r.longitude" +
            ") " +
            "FROM SavedRestaurant sr " +
            "JOIN sr.restaurant r " +
            "LEFT JOIN r.menus m " +
            "WHERE sr.user.userId = :userId " +
            "GROUP BY r.restaurantId, r.representativeImage, r.restaurantName, r.category, " +
            "         r.simpleDescription, r.latitude, r.longitude")
    Page<SavedRestaurantDto> findSavedRestaurantsByUserId(@Param("userId") Long userId, Pageable pageable);

}
