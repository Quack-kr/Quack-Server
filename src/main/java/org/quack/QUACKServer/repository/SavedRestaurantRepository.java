package org.quack.QUACKServer.repository;


import java.util.List;
import org.quack.QUACKServer.domain.Restaurant;
import org.quack.QUACKServer.domain.SavedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    int getCountByUserId(Long userId);

    List<Restaurant> findAllByUserId(Long userId);

    boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
