package org.quack.QUACKServer.domain.restaurant.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.vo
 * @fileName : RestaurantSubtractVo
 * @date : 25. 5. 4.
 */
@Getter
@NoArgsConstructor
public class RestaurantSubtractVo {
    private Long restaurantId;
    private String restaurantName;
    private RestaurantEnum.RestaurantCategoryType restaurantCategoryName;
    private Long distance;
    private Long averagePrice;
    private Long savedCounts;
    private Long likeCounts;
    private String openingHour;
    private Boolean isSaved;
}
