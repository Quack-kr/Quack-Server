package org.quack.QUACKServer.demo.repository;


import java.util.List;

import org.quack.QUACKServer.demo.domain.Restaurant;
import org.quack.QUACKServer.demo.domain.SavedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    int getCountByUser_UserId(Long userId);

    boolean existsByUser_UserIdAndRestaurant_RestaurantId(Long userId, Long restaurantId);

    List<Restaurant> findAllByUser_UserId(Long userId);
}
