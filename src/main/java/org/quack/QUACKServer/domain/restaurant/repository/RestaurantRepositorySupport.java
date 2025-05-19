package org.quack.QUACKServer.domain.restaurant.repository;


import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractByDistanceVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractByLikeVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractBySavedVo;
import org.quack.QUACKServer.domain.restaurant.vo.RestaurantSubtractSavedVo;

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

    RestaurantSubtractSavedVo findSavedSubtractRestaurant(Long restaurantId, Long customerUserId);

}
