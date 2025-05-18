package org.quack.QUACKServer.domain.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : RestaurantHours
 * @date : 25. 5. 18.
 */
@Entity
@Table(name = "restaurant_hours")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantHours {
    @Id
    @Column(name = "restaurant_hours_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantHoursId;

    @Column(name = "restaurant_id",nullable = false)
    private Long restaurantId;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "last_order_time")
    private LocalTime lastOrderTime;

    @Column(name = "is_closed")
    private Boolean isClosed;

}
