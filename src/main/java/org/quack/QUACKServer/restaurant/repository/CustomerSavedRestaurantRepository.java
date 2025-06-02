package org.quack.QUACKServer.restaurant.repository;

import org.quack.QUACKServer.restaurant.domain.CustomerSavedRestaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : CustomerSavedRestaurantRepository
 * @date : 25. 5. 27.
 */
public interface CustomerSavedRestaurantRepository extends JpaRepository<CustomerSavedRestaurant, Long> {
    @Query("SELECT COUNT (csr) FROM CustomerSavedRestaurant csr WHERE csr.customerUserId = :customerUserId")
    Long countByCustomerUserId(@Param("customerUserId") Long customerUserId);

    List<CustomerSavedRestaurant> findAllByCustomerUserId(Long customerUserId);

    Slice<CustomerSavedRestaurant> findAllByCustomerUserId(Long customerUserId, Pageable pageable);

    Optional<CustomerSavedRestaurant> findByCustomerUserIdAndRestaurantId(Long customerUserId, Long restaurantId);

    boolean existsByCustomerUserIdAndRestaurantId(Long customerUserId, Long restaurantId);
}
