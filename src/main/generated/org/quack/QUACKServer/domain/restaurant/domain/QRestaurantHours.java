package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurantHours is a Querydsl query type for RestaurantHours
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantHours extends EntityPathBase<RestaurantHours> {

    private static final long serialVersionUID = 1926193960L;

    public static final QRestaurantHours restaurantHours = new QRestaurantHours("restaurantHours");

    public final StringPath dayOfWeek = createString("dayOfWeek");

    public final BooleanPath isClosed = createBoolean("isClosed");

    public final TimePath<java.time.LocalTime> lastOrderTime = createTime("lastOrderTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> openTime = createTime("openTime", java.time.LocalTime.class);

    public final NumberPath<Long> restaurantHoursId = createNumber("restaurantHoursId", Long.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public QRestaurantHours(String variable) {
        super(RestaurantHours.class, forVariable(variable));
    }

    public QRestaurantHours(Path<? extends RestaurantHours> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantHours(PathMetadata metadata) {
        super(RestaurantHours.class, metadata);
    }

}

