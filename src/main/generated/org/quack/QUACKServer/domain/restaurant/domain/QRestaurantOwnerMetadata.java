package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurantOwnerMetadata is a Querydsl query type for RestaurantOwnerMetadata
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantOwnerMetadata extends EntityPathBase<RestaurantOwnerMetadata> {

    private static final long serialVersionUID = 1935139355L;

    public static final QRestaurantOwnerMetadata restaurantOwnerMetadata = new QRestaurantOwnerMetadata("restaurantOwnerMetadata");

    public final StringPath detailDescription = createString("detailDescription");

    public final StringPath effortMessage = createString("effortMessage");

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Long> restaurantOwnerMetadataId = createNumber("restaurantOwnerMetadataId", Long.class);

    public final StringPath simpleDescription = createString("simpleDescription");

    public QRestaurantOwnerMetadata(String variable) {
        super(RestaurantOwnerMetadata.class, forVariable(variable));
    }

    public QRestaurantOwnerMetadata(Path<? extends RestaurantOwnerMetadata> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantOwnerMetadata(PathMetadata metadata) {
        super(RestaurantOwnerMetadata.class, metadata);
    }

}

