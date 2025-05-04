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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantKeyword> restaurantKeyword = new ArrayList<>();

    private String detailAddress;

    @Column(columnDefinition = "GEOMETRY")
    private Point location;

    private String simpleDescription;

    private String detailDescription;

    private String effortMessage;

    private Long averagePrice;

    @Enumerated(EnumType.STRING)
    private RestaurantEnum.ParkingType parking;

    private Boolean toilet;

    // TODO : ENUM 정의
    private String service;


}

