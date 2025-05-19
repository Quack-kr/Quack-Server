package org.quack.QUACKServer.domain.restaurant.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantSubtractByDistanceVo
 * @date : 25. 5. 4.
 */
@Getter
@NoArgsConstructor
public class RestaurantSubtractByDistanceVo {
    private Long restaurantId;
    private String restaurantName;
    private Long averagePrice;
    private String simpleDescription;
    private LocalDateTime openTime;
    private LocalDateTime lastOrderTime;
    private Long distance;
    private Long longitude;
    private Long latitude;
    private Boolean isSaved;
}
