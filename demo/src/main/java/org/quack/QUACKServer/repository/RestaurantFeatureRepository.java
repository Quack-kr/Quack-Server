package org.quack.QUACKServer.repository;

import java.util.List;
import org.quack.QUACKServer.domain.RestaurantFeature;
import org.quack.QUACKServer.dto.restaurant.RestaurantFeatureDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantFeatureRepository extends JpaRepository<RestaurantFeature, Long> {

    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.RestaurantFeatureDto(" +
            "rf.featureCategory, rf.featureValue" + ") " +
            "FROM RestaurantFeature rf " +
            "WHERE rf.restaurant.restaurantId = :restaurantId")
    List<RestaurantFeatureDto> findFeaturesByRestaurantId(Long restaurantId);
}
