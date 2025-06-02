package org.quack.QUACKServer.restaurant.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantSimpleByDistanceVo
 * @date : 25. 5. 27.
 */
@Getter
@NoArgsConstructor
public class RestaurantSimpleByDistanceVo {

    private Long restaurantId;
    private String restaurantName;
    private Long averagePrice;
    private String simpleDescription;
    private Long distance;
    private Boolean isOpen;
}
