package org.quack.QUACKServer.restaurant.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantSubtractByDistanceVo
 * @date : 25. 5. 4.
 */
@Getter
@NoArgsConstructor
public class RestaurantSearchBySavedVo {
    private Long restaurantId;
    private String restaurantName;
    private Long averagePrice;
    private String simpleDescription;
    private Long savedCount;
    private Boolean isOpen;
}
