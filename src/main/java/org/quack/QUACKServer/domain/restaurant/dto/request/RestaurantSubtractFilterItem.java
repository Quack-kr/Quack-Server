package org.quack.QUACKServer.domain.restaurant.dto.request;

import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.dto.request
 * @fileName : RestaurantSubtractFilterItem
 * @date : 25. 5. 3.
 */

public record RestaurantSubtractFilterItem(List<RestaurantEnum.RestaurantCategoryType> restaurantCategoryTypes,
                                           List<RestaurantEnum.FavorFilterType> favorFilterTypes,
                                           List<RestaurantEnum.AtmosphereFilterType> atmosphereFilterTypes,
                                           List<RestaurantEnum.ToiletFilterType> toiletFilterTypes,
                                           List<RestaurantEnum.ServiceFilterType> serviceFilterTypes,
                                           List<RestaurantEnum.AtcFilterType> atcFilterTypes) {

}
