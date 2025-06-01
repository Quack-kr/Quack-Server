package org.quack.QUACKServer.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurantMetadata is a Querydsl query type for RestaurantMetadata
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantMetadata extends EntityPathBase<RestaurantMetadata> {

    private static final long serialVersionUID = -1904541794L;

    public static final QRestaurantMetadata restaurantMetadata = new QRestaurantMetadata("restaurantMetadata");

    public final NumberPath<Long> averagePrice = createNumber("averagePrice", Long.class);

    public final BooleanPath isUniSexToilet = createBoolean("isUniSexToilet");

    public final EnumPath<org.quack.QUACKServer.restaurant.enums.RestaurantEnum.ParkingType> parking = createEnum("parking", org.quack.QUACKServer.restaurant.enums.RestaurantEnum.ParkingType.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Long> restaurantMetadataId = createNumber("restaurantMetadataId", Long.class);

    public QRestaurantMetadata(String variable) {
        super(RestaurantMetadata.class, forVariable(variable));
    }

    public QRestaurantMetadata(Path<? extends RestaurantMetadata> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantMetadata(PathMetadata metadata) {
        super(RestaurantMetadata.class, metadata);
    }

}

