package org.quack.QUACKServer.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : RestaurantOwnerMetadata
 * @date : 25. 5. 18.
 */
@Entity
@Table(name = "restaurant_owner_metadata")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantOwnerMetadata {

    @Id
    @Column(name = "restaurant_owner_metadata_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantOwnerMetadataId;

    @Column(name = "restaurant_id" ,nullable = false)
    private Long restaurantId;

    @Column(name = "simple_description")
    private String simpleDescription;

    @Column(name = "detail_description")
    private String detailDescription;

    @Column(name = "effort_message")
    private String effortMessage;

}
