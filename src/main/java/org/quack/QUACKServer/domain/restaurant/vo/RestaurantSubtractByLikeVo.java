package org.quack.QUACKServer.domain.restaurant.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantSubtractBySavedVo
 * @date : 25. 5. 19.
 */
@NoArgsConstructor
@Getter
public class RestaurantSubtractByLikeVo {

    private Long restaurantId;
    private String restaurantName;
    private Long averagePrice;
    private String simpleDescription;
    private LocalDateTime openTime;
    private LocalDateTime lastOrderTime;
    private Long likedCount;
    private Boolean isSaved;
}
