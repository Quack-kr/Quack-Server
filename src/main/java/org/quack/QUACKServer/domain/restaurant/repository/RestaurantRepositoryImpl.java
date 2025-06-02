package org.quack.QUACKServer.domain.restaurant.repository;

import static org.quack.QUACKServer.domain.restaurant.domain.QRestaurantBreaks.restaurantBreaks;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.quack.QUACKServer.domain.menu.domain.QMenu;
import org.quack.QUACKServer.domain.menu.domain.QMenuEval;
import org.quack.QUACKServer.domain.menu.enums.MenuEnum;
import org.quack.QUACKServer.domain.restaurant.domain.*;
import org.quack.QUACKServer.domain.restaurant.dto.response.DayOperatingInfo;
import org.quack.QUACKServer.domain.restaurant.dto.response.RestaurantInfo;
import org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantFindDistanceFilter;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSearchFilter;
import org.quack.QUACKServer.domain.restaurant.filter.RestaurantSubtractFilter;
import org.quack.QUACKServer.domain.restaurant.vo.*;
import org.quack.QUACKServer.domain.review.domain.QReviewEvalSummary;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
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
public class RestaurantRepositoryImpl implements RestaurantRepositorySupport {

    private final JPAQueryFactory queryFactory;
    private final QRestaurant restaurant = QRestaurant.restaurant;
    private final QRestaurantMetadata restaurantMetadata = QRestaurantMetadata.restaurantMetadata;
    private final QRestaurantOwnerMetadata restaurantOwnerMetadata = QRestaurantOwnerMetadata.restaurantOwnerMetadata;
    private final QRestaurantCategory restaurantCategory = QRestaurantCategory.restaurantCategory;
    private final QReviewEvalSummary reviewEvalSummary = QReviewEvalSummary.reviewEvalSummary;
    private final QMenu menu = QMenu.menu;
    private final QRestaurantHours restaurantHours = QRestaurantHours.restaurantHours;
    private final QCustomerSavedRestaurant customerSavedRestaurant = QCustomerSavedRestaurant.customerSavedRestaurant;
    private final QMenuEval menuEval = QMenuEval.menuEval;

    @Override
    public List<RestaurantSubtractByDistanceVo> findAllBySubtractFilterOrderByDistance(RestaurantSubtractFilter filter) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Point userLocation = geometryFactory.createPoint(new Coordinate(filter.getLongitude(), filter.getLatitude()));

        NumberTemplate<Double> longitude = Expressions.numberTemplate(
                Double.class, "ST_X({0})", restaurant.location
        );

        NumberTemplate<Double> latitude = Expressions.numberTemplate(
                Double.class, "ST_Y({0})", restaurant.location
        );

        NumberTemplate<Double> distance = Expressions.numberTemplate(
                Double.class,
                "ST_Distance_Sphere({0}, {1})",
                restaurant.location,
                Expressions.constant(userLocation)
        );

        BooleanExpression isSaved = getIsSavedExpression(filter.getCustomerUserId());


        List<Long> restaurantIds = findRestaurantIdsBySubtractFilter(filter);

        return queryFactory
                .select(Projections.constructor(
                        RestaurantSubtractByDistanceVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        restaurantHours.openTime,
                        restaurantHours.lastOrderTime,
                        distance,
                        longitude,
                        latitude,
                        isSaved == null ? Expressions.constant(false) : isSaved
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .where(restaurant.restaurantId.in(restaurantIds))
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .groupBy(restaurant.restaurantId)
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .fetch();
    }

    @Override
    public List<RestaurantSubtractByLikeVo> findAllBySubtractFilterOrderByLike(RestaurantSubtractFilter filter) {

        BooleanExpression isSaved = getIsSavedExpression(filter.getCustomerUserId());

        List<Long> restaurantIds = findRestaurantIdsBySubtractFilter(filter);

        return queryFactory
                .select(Projections.constructor(
                        RestaurantSubtractByLikeVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        restaurantHours.openTime,
                        restaurantHours.lastOrderTime,
                        menuEval.count(),
                        isSaved == null ? Expressions.constant(false) : isSaved
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(menu).on(menu.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(menuEval).on(menuEval.menuId.eq(menu.menuId))
                .where(restaurant.restaurantId.in(restaurantIds).and(menuEval.menuEvalType.eq(MenuEnum.MenuEvalType.CRAZY)))
                .groupBy(restaurant.restaurantId)
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .fetch();

    }

    @Override
    public List<RestaurantSubtractBySavedVo> findAllBySubtractFilterOrderBySaved(RestaurantSubtractFilter filter) {
        BooleanExpression isSaved = getIsSavedExpression(filter.getCustomerUserId());

        List<Long> restaurantIds = findRestaurantIdsBySubtractFilter(filter);

        return queryFactory
                .select(Projections.constructor(
                        RestaurantSubtractBySavedVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        restaurantHours.openTime,
                        restaurantHours.lastOrderTime,
                        customerSavedRestaurant.count(),
                        isSaved == null ? Expressions.constant(false) : isSaved
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .join(customerSavedRestaurant).on(customerSavedRestaurant.restaurantId.eq(restaurant.restaurantId))
                .where(restaurant.restaurantId.in(restaurantIds).and(menuEval.menuEvalType.eq(MenuEnum.MenuEvalType.CRAZY)))
                .groupBy(restaurant.restaurantId)
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .fetch();
    }

    @Override
    public Slice<RestaurantSearchByDistanceVo> findByRestaurantNameOrderByDistance(RestaurantSearchFilter filter) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Point userLocation = geometryFactory.createPoint(new Coordinate(filter.getLongitude(), filter.getLatitude()));

        NumberTemplate<Double> distance = Expressions.numberTemplate(
                Double.class,
                "ST_Distance_Sphere({0}, {1})",
                restaurant.location,
                Expressions.constant(userLocation)
        );

        BooleanExpression isOpen = Expressions.booleanTemplate("NOT {0}", restaurantHours.isClosed);

        List<RestaurantSearchByDistanceVo> restaurants = queryFactory
                .select(Projections.constructor(
                        RestaurantSearchByDistanceVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        distance,
                        isOpen
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .where(restaurant.restaurantName.containsIgnoreCase(filter.getKeyword())
                        .and(restaurantHours.isClosed.eq(!filter.isOpen())))
                .groupBy(restaurant.restaurantId)
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .fetch();

        boolean hasNext = restaurants.size() > filter.getPageable().getPageSize();

        if (hasNext) {
            restaurants.remove(filter.getPageable().getPageSize());
        }

        return new SliceImpl<>(restaurants);
    }

    @Override
    public Slice<RestaurantSearchByLikeVo> findByRestaurantNameOrderByLike(RestaurantSearchFilter filter) {

        BooleanExpression isOpen = Expressions.booleanTemplate("NOT {0}", restaurantHours.isClosed);

        List<RestaurantSearchByLikeVo> restaurants = queryFactory
                .select(Projections.constructor(
                        RestaurantSearchByLikeVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        menuEval.count(),
                        isOpen
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(menu).on(menu.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(menuEval).on(menuEval.menuId.eq(menu.menuId))
                .where(restaurant.restaurantName.containsIgnoreCase(filter.getKeyword())
                        .and(restaurantHours.isClosed.eq(!filter.isOpen())))
                .groupBy(restaurant.restaurantId)
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .fetch();

        boolean hasNext = restaurants.size() > filter.getPageable().getPageSize();

        if (hasNext) {
            restaurants.remove(filter.getPageable().getPageSize());
        }

        return new SliceImpl<>(restaurants);
    }

    @Override
    public Slice<RestaurantSearchBySavedVo> findByRestaurantNameOrderBySaved(RestaurantSearchFilter filter) {
        BooleanExpression isOpen = Expressions.booleanTemplate("NOT {0}", restaurantHours.isClosed);

        List<RestaurantSearchBySavedVo> restaurants = queryFactory
                .select(Projections.constructor(
                        RestaurantSearchBySavedVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        customerSavedRestaurant.count(),
                        isOpen
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .join(customerSavedRestaurant).on(customerSavedRestaurant.restaurantId.eq(restaurant.restaurantId))
                .where(restaurant.restaurantName.containsIgnoreCase(filter.getKeyword())
                        .and(restaurantHours.isClosed.eq(!filter.isOpen()))
                        .and(menuEval.menuEvalType.eq(MenuEnum.MenuEvalType.CRAZY)))
                .groupBy(restaurant.restaurantId)
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), filter.getSortType()))
                .offset(filter.getPageable().getOffset())
                .limit(filter.getPageable().getPageSize() + 1)
                .fetch();

        boolean hasNext = restaurants.size() > filter.getPageable().getPageSize();

        if (hasNext) {
            restaurants.remove(filter.getPageable().getPageSize());
        }

        return new SliceImpl<>(restaurants);
    }

    @Override
    public List<RestaurantSimpleByDistanceVo> findDistanceByRestaurants(RestaurantFindDistanceFilter filter) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Point userLocation = geometryFactory.createPoint(new Coordinate(filter.getLongitude(), filter.getLatitude()));

        NumberTemplate<Double> longitude = Expressions.numberTemplate(
                Double.class, "ST_X({0})", restaurant.location
        );

        NumberTemplate<Double> latitude = Expressions.numberTemplate(
                Double.class, "ST_Y({0})", restaurant.location
        );

        NumberTemplate<Double> distance = Expressions.numberTemplate(
                Double.class,
                "ST_Distance_Sphere({0}, {1})",
                restaurant.location,
                Expressions.constant(userLocation)
        );
        BooleanExpression isOpen = Expressions.booleanTemplate("NOT {0}", restaurantHours.isClosed);

        return queryFactory
                .select(Projections.constructor(
                        RestaurantSimpleByDistanceVo.class,
                        restaurant.restaurantId,
                        restaurant.restaurantName,
                        restaurantMetadata.averagePrice,
                        restaurantOwnerMetadata.simpleDescription,
                        distance,
                        longitude,
                        latitude,
                        isOpen
                ))
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurant.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .where(restaurant.restaurantId.in(filter.getRestaurantIds())
                        .and(restaurantHours.isClosed.eq(!filter.isOpen())))
                .orderBy(builderOrderBySubtractFilter(filter.getLongitude(), filter.getLatitude(), RestaurantEnum.RestaurantSortType.DISTANCE))
                .groupBy(restaurant.restaurantId)
                .fetch();
    }

    @Override
    public RestaurantInfo findRestaurantInfoByRestaurantId(Long restaurantId) {
        Tuple tuple = queryFactory
                .select(restaurant, restaurantOwnerMetadata)
                .from(restaurant)
                .leftJoin(restaurantOwnerMetadata).on(restaurantOwnerMetadata.restaurantId.eq(restaurantId))
                .where(restaurant.restaurantId.eq(restaurantId))
                .fetchOne();

        List<String> restaurantAreaList = new ArrayList<>();
        List<RestaurantArea> restaurantAreas = tuple.get(restaurant.restaurantAreas);

        for (RestaurantArea restaurantArea : restaurantAreas) {
            restaurantAreaList.add(restaurantArea.getRestaurantAreaName());
        }

        List<String> restaurantCategoryList = new ArrayList<>();
        List<RestaurantCategory> restaurantCategories = tuple.get(restaurant.restaurantCategories);

        for (RestaurantCategory category : restaurantCategories) {
            restaurantCategoryList.add(category.getRestaurantCategoryName().getDescription());
        }

        return RestaurantInfo.of(
                tuple.get(restaurant.restaurantName),
                restaurantCategoryList,
                restaurantAreaList,
                tuple.get(restaurant.detailAddress),
                tuple.get(restaurant.location),
                tuple.get(restaurantOwnerMetadata.simpleDescription),
                tuple.get(restaurantOwnerMetadata.detailDescription),
                tuple.get(restaurantOwnerMetadata.effortMessage)
        );
    }

    public Map<String, DayOperatingInfo> findOperationInfo(Long restaurantId) {
        List<String> dayOfWeek = List.of("월", "화", "수", "목", "금", "토", "일");

        List<RestaurantHours> hoursList = queryFactory
                .selectFrom(restaurantHours)
                .where(restaurantHours.restaurantId.eq(restaurantId))
                .fetch();


        List<RestaurantBreaks> breaksList = queryFactory
                .selectFrom(restaurantBreaks)
                .where(restaurantBreaks.restaurantId.eq(restaurantId))
                .fetch();


        Map<String, RestaurantHours> hoursMap = hoursList.stream()
                .collect(Collectors.toMap(
                        RestaurantHours::getDayOfWeek,
                        Function.identity()
                ));


        Map<String, RestaurantBreaks> breaksMap = breaksList.stream()
                .collect(Collectors.toMap(
                        RestaurantBreaks::getDayOfWeek,
                        Function.identity()
                ));


        Map<String, DayOperatingInfo> result = new LinkedHashMap<>();

        for (String day : dayOfWeek) {
            RestaurantHours hourInfo = hoursMap.get(day);
            RestaurantBreaks breakInfo = breaksMap.get(day);

            result.put(day, DayOperatingInfo.of(
                    hourInfo != null ? hourInfo.getOpenTime() : null,
                    hourInfo != null ? hourInfo.getCloseTime() : null,
                    hourInfo != null ? hourInfo.getLastOrderTime() : null,
                    breakInfo != null ? breakInfo.getBreakStart() : null,
                    breakInfo != null ? breakInfo.getBreakEnd() : null,
                    breakInfo != null ? breakInfo.getLastOrderTime() : null,
                    hourInfo != null ? hourInfo.getIsClosed() : null
            ));
        }

        return result;
    }

    protected BooleanBuilder builderConditionSubtractFilter(RestaurantSubtractFilter filter) {
        BooleanBuilder builder = new BooleanBuilder();

        if (filter.getIsUniSexToilet()) {
            builder.and(restaurantMetadata.isUniSexToilet.eq(false));
        }

        if (!CollectionUtils.isEmpty(filter.getParkingTypes())) {
            builder.and(restaurantMetadata.parking.notIn(filter.getParkingTypes()));
        }

        if (!CollectionUtils.isEmpty(filter.getRestaurantCategoryTypes())) {
            builder.and(restaurantCategory.restaurantCategoryName.notIn(filter.getRestaurantCategoryTypes()));
        }

        if (!CollectionUtils.isEmpty(filter.getReviewTags())) {
            builder.and(reviewEvalSummary.reviewSummaryTag.notIn(filter.getReviewTags()));
        }

        if (filter.getIsOpen()) {
            builder.and(restaurantHours.isClosed.eq(false));
        }


        if (filter.getIsSaved()) {
            builder.and(getIsSavedExpression(filter.getCustomerUserId()).eq(true));
        }

        return builder;
    }

    protected BooleanExpression getIsSavedExpression(Long customerUserId) {
        return customerUserId == null ? null : JPAExpressions.selectOne()
                .from(customerSavedRestaurant)
                .where(
                        customerSavedRestaurant.customerUserId.eq(customerUserId),
                        customerSavedRestaurant.restaurantId.eq(restaurant.restaurantId))
                .exists();
    }

    protected OrderSpecifier<?> builderOrderBySubtractFilter(double longitude, double latitude, RestaurantEnum.RestaurantSortType sortType) {

        switch (sortType) {
            // 거리순
            case DISTANCE -> {
                return new OrderSpecifier<>(
                        Order.ASC,
                        Expressions.numberTemplate(
                                Double.class,
                                "ST_Distance_Sphere(POINT{0}, {1}, {2})",
                                longitude, latitude, restaurant.location)
                );
            }
            // 저장순
            case SAVED -> {
                return new OrderSpecifier<>(
                        Order.DESC,
                        customerSavedRestaurant.count()
                );
            }
            // 미친맛순
            case LIKE -> {
                return new OrderSpecifier<>(
                        Order.DESC,
                        menuEval.count()
                );
            }
            case null, default -> {
                throw new IllegalArgumentException();
            }
        }

    }

    protected List<Long> findRestaurantIdsBySubtractFilter(RestaurantSubtractFilter filter) {
        return queryFactory
                .selectDistinct(restaurant.restaurantId)
                .from(restaurant)
                .join(restaurantMetadata).on(restaurantMetadata.restaurantId.eq(restaurantMetadata.restaurantId))
                .join(restaurantHours).on(restaurantHours.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(restaurantCategory).on(restaurantCategory.restaurant.restaurantId.eq(restaurant.restaurantId))
                .leftJoin(reviewEvalSummary).on(reviewEvalSummary.restaurantId.eq(reviewEvalSummary.restaurantId), reviewEvalSummary.historyAt.eq(LocalDate.now().minusDays(1)))
                .where(builderConditionSubtractFilter(filter)).fetch();
    }

}


