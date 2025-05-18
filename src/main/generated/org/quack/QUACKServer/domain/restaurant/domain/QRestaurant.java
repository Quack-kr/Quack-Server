package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 778105895L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final StringPath detailAddress = createString("detailAddress");

    public final SimplePath<org.springframework.data.geo.Point> location = createSimple("location", org.springframework.data.geo.Point.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final StringPath restaurantName = createString("restaurantName");

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

