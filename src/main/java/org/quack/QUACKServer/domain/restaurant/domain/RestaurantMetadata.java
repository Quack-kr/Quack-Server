package org.quack.QUACKServer.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : RestaurantMetadata
 * @date : 25. 5. 18.
 */
@Entity
@Table(name = "restaurant_metadata")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantMetadata {
    @Id
    @Column(name = "restaurant_metadata_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantMetadataId;

    @Column(name = "restaurant_id" ,nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking")
    private RestaurantEnum.ParkingType parking;

    @Column(name = "average_price")
    private Long averagePrice;

    @Column(name = "is_unisex_toilet")
    private Boolean isUniSexToilet;

}
