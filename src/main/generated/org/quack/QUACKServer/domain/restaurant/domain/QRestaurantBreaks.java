package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurantBreaks is a Querydsl query type for RestaurantBreaks
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantBreaks extends EntityPathBase<RestaurantBreaks> {

    private static final long serialVersionUID = -587026853L;

    public static final QRestaurantBreaks restaurantBreaks = new QRestaurantBreaks("restaurantBreaks");

    public final TimePath<java.time.LocalTime> breakEnd = createTime("breakEnd", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> breakStart = createTime("breakStart", java.time.LocalTime.class);

    public final StringPath dayOfWeek = createString("dayOfWeek");

    public final TimePath<java.time.LocalTime> lastOrderTime = createTime("lastOrderTime", java.time.LocalTime.class);

    public final NumberPath<Long> restaurantBreaksId = createNumber("restaurantBreaksId", Long.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public QRestaurantBreaks(String variable) {
        super(RestaurantBreaks.class, forVariable(variable));
    }

    public QRestaurantBreaks(Path<? extends RestaurantBreaks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantBreaks(PathMetadata metadata) {
        super(RestaurantBreaks.class, metadata);
    }

}

