package org.quack.QUACKServer.domain.restaurant.repository;

import org.quack.QUACKServer.domain.restaurant.domain.RestaurantMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantMetadataRepository
 * @date : 25. 5. 19.
 */
@Repository
public interface RestaurantMetadataRepository extends JpaRepository<RestaurantMetadata, Long> {

    Optional<RestaurantMetadata> findByRestaurantId(Long restaurantId);

}
