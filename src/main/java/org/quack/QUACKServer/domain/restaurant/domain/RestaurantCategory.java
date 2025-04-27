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
 * @fileName : RestaurantCategory
 * @date : 25. 4. 27.
 */
@Entity
@Table(name = "restaurant_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantCategoryId;

    @Enumerated(EnumType.STRING)
    private RestaurantEnum.RestaurantCategoryType restaurantCategoryName;
}
