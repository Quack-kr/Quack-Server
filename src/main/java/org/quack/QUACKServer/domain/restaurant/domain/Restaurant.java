package org.quack.QUACKServer.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private List<RestaurantArea> restaurantArea;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<RestaurantCategory> category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_keyword_id")
    private List<RestaurantKeyword> restaurantKeyword;

    private String detailAddress;

    @Column(precision = 10, scale = 7)
    private Double latitude;

    @Column(precision = 10, scale = 7)
    private Double longitude;

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

