package org.quack.QUACKServer.domain.restaurant.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.restaurant.domain.QCustomerSavedRestaurant;
import org.quack.QUACKServer.domain.restaurant.domain.QRestaurant;
import org.quack.QUACKServer.domain.restaurant.dto.response.SubtractRestaurantItem;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;
import org.quack.QUACKServer.domain.review.domain.QReview;
import org.quack.QUACKServer.domain.review.domain.QReviewSummary;
import org.springframework.stereotype.Repository;
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
    private final QCustomerSavedRestaurant customerSavedRestaurant = QCustomerSavedRestaurant.customerSavedRestaurant;
    private final QReview review = QReview.review;
    private final QReviewSummary  reviewSummary = QReviewSummary.reviewSummary;

    @Override
    public List<SubtractRestaurantItem> findAllBySubtractFilterOrderByDistance(RestaurantSubtractFilter filter) {

//        List<Restaurant> restaurants = queryFactory
//                .selectFrom(Expressions.)
//                .from(restaurant)
//                .leftJoin(review).on(review.restaurantId.eq(restaurant.restaurantId))
//                .orderBy(
//                        Expressions.numberTemplate(Double.class,
//                                "ST_Distance_Sphere(POINT({0}, {1}), {2})",
//                                filter.getLongitude(), filter.getLatitude(), restaurant.location
//                        ).asc()
//                )
//                .fetch();

        return List.of();
    }

    @Override
    public List<SubtractRestaurantItem> findAllBySubtractFilterOrderByLike(RestaurantSubtractFilter filter) {
        return List.of();
    }

    @Override
    public List<SubtractRestaurantItem> findAllBySubtractFilterOrderBySaved(RestaurantSubtractFilter filter) {
        return List.of();
    }

    protected BooleanBuilder builderSubtractFilter() {
        BooleanBuilder builder = new BooleanBuilder();
        return builder;
    }

//    public OrderSpecifier<?> getOrderBy(double longitude, double latitude, RestaurantEnum.RestaurantSortType sortType) {
//
//        switch (sortType) {
//            // 거리순
//            case DISTANCE -> {
//                return new OrderSpecifier<>(
//                        Order.ASC,
//                        Expressions.numberTemplate(
//                                Double.class,
//                                "ST_Distance_Sphere(POINT{0}, {1}, {2})",
//                                longitude, latitude, restaurant.location)
//                );
//            }
//            // 저장순
//            case SAVED -> {
//                return new OrderSpecifier<>(
//                        Order.DESC,
//                        JPAExpressions
//                                .select(customerSavedRestaurant.count())
//                                .of(customerSavedRestaurant)
//                                .where(customerSavedRestaurant.restaurant.eq(restaurant))
//                );
//            }
//            // 미친맛순
//            case LIKE -> {
//                return new OrderSpecifier<>(
//                        Order.DESC,
//                        JPAExpressions
//                            .select(customerSavedRestaurant.count())
//                );
//            }
//
//            case null, default -> {
//                // 기본은 최신순이다.
//                return new OrderSpecifier<>(
//                        Order.DESC,
//                        restaurant.restaurantId
//                );
//            }
//        }
//    }




}
