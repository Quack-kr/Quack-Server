package org.quack.QUACKServer.domain.restaurant.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.domain.QRestaurant;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.repository
 * @fileName : RestaurantRepositoryImpl
 * @date : 25. 4. 29.
 */

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {


    private final JPAQueryFactory queryFactory;
    private final QRestaurant restaurant = QRestaurant.restaurant;

    @Override
    public List<Restaurant> findAllBySubtractFilterAndSortFilter(RestaurantSubtractFilter filter) {

        List<Restaurant> restaurants = queryFactory
                .select(restaurant)
                .from(restaurant)
//                .join(review)
//                .where(restaurant.notIn())
                .fetch();


        return List.of();
    }

    public OrderSpecifier<?> getOrderBy(double longitude, double latitude, RestaurantEnum.RestaurantSortType sortType) {

        switch (sortType) {
            case DISTANCE -> {
                return new OrderSpecifier<>(
                        Order.ASC,
                        Expressions.numberTemplate(
                                Double.class,
                                "ST_Distance_Sphere(POINT{0}, {1}, {2})",
                                longitude, latitude, restaurant.location)
                );
            }
            case null, default -> {
                return new OrderSpecifier<>(
                        Order.DESC,
                        restaurant.restaurantId
                );
            }
        }
    }




}
