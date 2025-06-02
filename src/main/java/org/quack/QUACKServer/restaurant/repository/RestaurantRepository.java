package org.quack.QUACKServer.restaurant.repository;

import org.quack.QUACKServer.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantRepository
 * @date : 25. 4. 29.
 */

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositorySupport {

}
