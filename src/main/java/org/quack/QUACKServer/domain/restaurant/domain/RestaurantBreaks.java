package org.quack.QUACKServer.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : RestaurantBreaks
 * @date : 25. 5. 18.
 */

@Entity
@Table(name = "restaurant_breaks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantBreaks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_breaks_id")
    private Long restaurantBreaksId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "break_start")
    private LocalTime breakStart;

    @Column(name = "break_end")
    private LocalTime breakEnd;

    @Column(name = "last_order_time")
    private LocalTime lastOrderTime;
}
