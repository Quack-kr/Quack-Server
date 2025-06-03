package org.quack.QUACKServer.restaurant.repository;


import java.util.Map;
import org.quack.QUACKServer.restaurant.dto.response.DayOperatingInfo;
import org.quack.QUACKServer.restaurant.dto.response.RestaurantInfo;
import org.quack.QUACKServer.restaurant.filter.RestaurantFindDistanceFilter;
import org.quack.QUACKServer.restaurant.filter.RestaurantSearchFilter;
import org.quack.QUACKServer.restaurant.filter.RestaurantSubtractFilter;
import org.quack.QUACKServer.restaurant.vo.*;
import org.springframework.data.domain.Slice;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantRepositorySupport
 * @date : 25. 4. 29.
 */

public interface RestaurantRepositorySupport {

    List<RestaurantSubtractByDistanceVo> findAllBySubtractFilterOrderByDistance(RestaurantSubtractFilter filter);

    List<RestaurantSubtractByLikeVo> findAllBySubtractFilterOrderByLike(RestaurantSubtractFilter filter);

    List<RestaurantSubtractBySavedVo> findAllBySubtractFilterOrderBySaved(RestaurantSubtractFilter filter);

    Slice<RestaurantSearchByDistanceVo> findByRestaurantNameOrderByDistance(RestaurantSearchFilter filter);

    Slice<RestaurantSearchByLikeVo> findByRestaurantNameOrderByLike(RestaurantSearchFilter filter);

    Slice<RestaurantSearchBySavedVo> findByRestaurantNameOrderBySaved(RestaurantSearchFilter filter);

    List<RestaurantSimpleByDistanceVo> findDistanceByRestaurants(RestaurantFindDistanceFilter filter);

    RestaurantInfo findRestaurantInfoByRestaurantId(Long restaurantId);

    Map<String, DayOperatingInfo> findOperationInfo(Long restaurantId);
}
