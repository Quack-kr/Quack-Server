package org.quack.QUACKServer.domain.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchByDistanceVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchByLikeVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSearchBySavedVo;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.response
 * @fileName : SearchRestaurantsByKeywordItem
 * @date : 25. 5. 24.
 */

@Getter
@Builder
public class SearchRestaurantsByKeywordItem {
    private Long restaurantId;
    private String description;
    private List<RestaurantEnum.RestaurantCategoryType> categories;
    private Long distance;
    private Long likedCount;
    private Long savedCount;
    private Long averagePrice;
    private Boolean isOpen;

    public static SearchRestaurantsByKeywordItem of(RestaurantSearchByDistanceVo vo, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SearchRestaurantsByKeywordItem.builder()
                .restaurantId(vo.getRestaurantId())
                .isOpen(vo.getIsOpen())
                .averagePrice(vo.getAveragePrice())
                .description(vo.getSimpleDescription())
                .distance(vo.getDistance())
                .categories(categories)
                .build();
    }

    public static SearchRestaurantsByKeywordItem of(RestaurantSearchByLikeVo vo, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SearchRestaurantsByKeywordItem.builder()
                .restaurantId(vo.getRestaurantId())
                .isOpen(vo.getIsOpen())
                .averagePrice(vo.getAveragePrice())
                .description(vo.getSimpleDescription())
                .likedCount(vo.getLikedCount())
                .categories(categories)
                .build();
    }

    public static SearchRestaurantsByKeywordItem of(RestaurantSearchBySavedVo vo, List<RestaurantEnum.RestaurantCategoryType> categories) {
        return SearchRestaurantsByKeywordItem.builder()
                .restaurantId(vo.getRestaurantId())
                .isOpen(vo.getIsOpen())
                .averagePrice(vo.getAveragePrice())
                .description(vo.getSimpleDescription())
                .savedCount(vo.getSavedCount())
                .categories(categories)
                .build();
    }
}
