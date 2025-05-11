package org.quack.QUACKServer.domain.restaurant.repository;


import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.dto.response.SubtractRestaurantItem;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantRepositoryCustom
 * @date : 25. 4. 29.
 */

public interface RestaurantRepositoryCustom {

    List<SubtractRestaurantItem> findAllBySubtractFilterOrderByDistance(RestaurantSubtractFilter filter);

    List<SubtractRestaurantItem> findAllBySubtractFilterOrderByLike(RestaurantSubtractFilter filter);

    List<SubtractRestaurantItem> findAllBySubtractFilterOrderBySaved(RestaurantSubtractFilter filter);
}
