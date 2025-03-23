package org.quack.QUACKServer.repository;

import java.util.List;
import java.util.Optional;
import org.quack.QUACKServer.domain.RestaurantOperating;
import org.quack.QUACKServer.domain.common.DayOfWeek;
import org.quack.QUACKServer.dto.restaurant.OperatingInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOperatingRepository extends JpaRepository<RestaurantOperating, Long> {

    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.OperatingInfoDto(" +
            "ro.dayOfWeek, ro.startTime, ro.endTime, ro.breakStartTime, ro.breakEndTime, ro.lastOrderTime, ro.isHoliday) " +
            "FROM RestaurantOperating ro " +
            "WHERE ro.restaurant.restaurantId = :restaurantId")
    List<OperatingInfoDto> findOperatingInfoByRestaurantId(Long restaurantId);

    Optional<RestaurantOperating> findByRestaurant_RestaurantIdAndDayOfWeek(Long restaurantId, DayOfWeek dayOfWeek);


}
