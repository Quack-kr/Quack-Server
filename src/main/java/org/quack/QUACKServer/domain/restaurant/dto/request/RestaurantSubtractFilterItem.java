package org.quack.QUACKServer.domain.restaurant.dto.request;

import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;

import java.util.List;

/**
 * @param menuFilters           1. 메뉴 필터
 * @param favorFilters          2. 맛 필터
 * @param atmosphereFilterTypes 3. 분위기 필터
 * @param toiletFilterTypes     4. 화장실 필터
 * @param serviceFilterTypes    5. 주차/서비스
 * @param atcFilterTypes        6. 기타
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
