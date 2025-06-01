package org.quack.QUACKServer.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : RestaurantArea
 * @date : 25. 4. 27.
 */
@Entity
@Table(name = "restaurant_area")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantAreaId;

    private String restaurantAreaName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
