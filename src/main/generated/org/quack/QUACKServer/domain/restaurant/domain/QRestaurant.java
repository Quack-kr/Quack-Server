package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 778105895L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final NumberPath<Long> averagePrice = createNumber("averagePrice", Long.class);

    public final ListPath<RestaurantCategory, QRestaurantCategory> category = this.<RestaurantCategory, QRestaurantCategory>createList("category", RestaurantCategory.class, QRestaurantCategory.class, PathInits.DIRECT2);

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath detailDescription = createString("detailDescription");

    public final StringPath effortMessage = createString("effortMessage");

    public final BooleanPath isUnisexToilet = createBoolean("isUnisexToilet");

    public final EnumPath<org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ParkingType> parking = createEnum("parking", org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ParkingType.class);

    public final ListPath<RestaurantArea, QRestaurantArea> restaurantArea = this.<RestaurantArea, QRestaurantArea>createList("restaurantArea", RestaurantArea.class, QRestaurantArea.class, PathInits.DIRECT2);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final SimplePath<java.awt.Point> restaurantLocation = createSimple("restaurantLocation", java.awt.Point.class);

    public final StringPath restaurantName = createString("restaurantName");

    public final StringPath service = createString("service");

    public final StringPath simpleDescription = createString("simpleDescription");

    public QRestaurant(String variable) {
        super(Restaurant.class, forVariable(variable));
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurant(PathMetadata metadata) {
        super(Restaurant.class, metadata);
    }

}

