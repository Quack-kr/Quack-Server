package org.quack.QUACKServer.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : Restaurant
 * @date : 25. 4. 27.
 */
@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantArea> restaurantArea = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantCategory> category = new ArrayList<>();

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(columnDefinition = "GEOMETRY" , name = "restaurant_location")
    private Point restaurantLocation;

    @Column(name = "simple_description")
    private String simpleDescription;

    @Column(name = "detail_description")
    private String detailDescription;

    @Column(name = "effort_message")
    private String effortMessage;

    @Column(name = "average_price")
    private Long averagePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking")
    private RestaurantEnum.ParkingType parking;

    // 남녀 공용 여부
    @Column(name = "unisex_toilet_flag")
    private Boolean isUnisexToilet;

    // TODO : ENUM 정의
    private String service;


}

