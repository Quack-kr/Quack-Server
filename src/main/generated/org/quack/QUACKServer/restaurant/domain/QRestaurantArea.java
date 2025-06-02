package org.quack.QUACKServer.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurantArea is a Querydsl query type for RestaurantArea
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantArea extends EntityPathBase<RestaurantArea> {

    private static final long serialVersionUID = -602610564L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurantArea restaurantArea = new QRestaurantArea("restaurantArea");

    public final QRestaurant restaurant;

    public final NumberPath<Long> restaurantAreaId = createNumber("restaurantAreaId", Long.class);

    public final StringPath restaurantAreaName = createString("restaurantAreaName");

    public QRestaurantArea(String variable) {
        this(RestaurantArea.class, forVariable(variable), INITS);
    }

    public QRestaurantArea(Path<? extends RestaurantArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurantArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurantArea(PathMetadata metadata, PathInits inits) {
        this(RestaurantArea.class, metadata, inits);
    }

    public QRestaurantArea(Class<? extends RestaurantArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant")) : null;
    }

}

