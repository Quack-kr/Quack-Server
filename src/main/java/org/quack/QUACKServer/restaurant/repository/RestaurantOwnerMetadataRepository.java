package org.quack.QUACKServer.restaurant.repository;

import org.quack.QUACKServer.restaurant.domain.RestaurantOwnerMetadata;
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
public interface RestaurantOwnerMetadataRepository extends JpaRepository<RestaurantOwnerMetadata, Long> {

    Optional<RestaurantOwnerMetadata> findByRestaurantId(Long restaurantId);

}
