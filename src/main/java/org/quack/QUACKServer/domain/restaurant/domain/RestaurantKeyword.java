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
 * @fileName : RestaurantKeyword
 * @date : 25. 4. 27.
 */

@Entity
@Table(name = "restaurant_keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantKeywordId;

    @Enumerated(EnumType.STRING)
    private RestaurantEnum.RestaurantKeyword content;

    @Enumerated(EnumType.STRING)
    private RestaurantEnum.ReviewKeywordType restaurantKeywordType;

}
