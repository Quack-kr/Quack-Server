package org.quack.QUACKServer.domain.restaurant.repository;

import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantCategoryRepository
 * @date : 25. 5. 18.
 */
@Repository
public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Long> {
    @Query("SELECT rc FROM RestaurantCategory rc WHERE rc.restaurant.restaurantId IN :retaruantIds")
    List<RestaurantCategory> findAllByRestaurants(@Param("restaurantIds") List<Long> restaurantIds);

    @Query(value = "SELECT * FROM restaurant_category WHERE restaurant_id = :restaurantId LIMIT 1", nativeQuery = true)
    Optional<RestaurantCategory> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<RestaurantCategory> findAllByRestaurant(Restaurant restaurant);
}
