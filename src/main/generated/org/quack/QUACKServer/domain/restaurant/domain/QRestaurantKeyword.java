package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurantKeyword is a Querydsl query type for RestaurantKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantKeyword extends EntityPathBase<RestaurantKeyword> {

    private static final long serialVersionUID = -1973413950L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurantKeyword restaurantKeyword = new QRestaurantKeyword("restaurantKeyword");

    public final EnumPath<org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.RestaurantKeyword> content = createEnum("content", org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.RestaurantKeyword.class);

    public final QRestaurant restaurant;

    public final NumberPath<Long> restaurantKeywordId = createNumber("restaurantKeywordId", Long.class);

    public final EnumPath<org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ReviewKeywordType> restaurantKeywordType = createEnum("restaurantKeywordType", org.quack.QUACKServer.domain.restaurant.enums.RestaurantEnum.ReviewKeywordType.class);

    public QRestaurantKeyword(String variable) {
        this(RestaurantKeyword.class, forVariable(variable), INITS);
    }

    public QRestaurantKeyword(Path<? extends RestaurantKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurantKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurantKeyword(PathMetadata metadata, PathInits inits) {
        this(RestaurantKeyword.class, metadata, inits);
    }

    public QRestaurantKeyword(Class<? extends RestaurantKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant")) : null;
    }

}

