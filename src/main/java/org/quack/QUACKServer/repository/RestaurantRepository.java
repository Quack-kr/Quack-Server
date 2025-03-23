package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.Restaurant;
import org.quack.QUACKServer.dto.restaurant.RestaurantBasicInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.RestaurantBasicInfoDto(" +
            "r.restaurantId, r.restaurantName, r.representativeImage, r.address, r.category, r.simpleDescription, " +
            "r.detailDescription, r.latitude, r.longitude" +
            ") " +
            "FROM Restaurant r " +
            "WHERE r.restaurantId = :restaurantId")
    RestaurantBasicInfoDto findBasicInfoByRestaurantId(Long restaurantId);
}
