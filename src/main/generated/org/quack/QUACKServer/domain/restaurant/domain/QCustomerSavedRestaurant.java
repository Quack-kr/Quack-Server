package org.quack.QUACKServer.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerSavedRestaurant is a Querydsl query type for CustomerSavedRestaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerSavedRestaurant extends EntityPathBase<CustomerSavedRestaurant> {

    private static final long serialVersionUID = -113394500L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerSavedRestaurant customerSavedRestaurant = new QCustomerSavedRestaurant("customerSavedRestaurant");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> customerSavedRestaurantId = createNumber("customerSavedRestaurantId", Long.class);

    public final QRestaurant restaurant;

    public final org.quack.QUACKServer.domain.user.domain.QUser userId;

    public QCustomerSavedRestaurant(String variable) {
        this(CustomerSavedRestaurant.class, forVariable(variable), INITS);
    }

    public QCustomerSavedRestaurant(Path<? extends CustomerSavedRestaurant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerSavedRestaurant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerSavedRestaurant(PathMetadata metadata, PathInits inits) {
        this(CustomerSavedRestaurant.class, metadata, inits);
    }

    public QCustomerSavedRestaurant(Class<? extends CustomerSavedRestaurant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant")) : null;
        this.userId = inits.isInitialized("userId") ? new org.quack.QUACKServer.domain.user.domain.QUser(forProperty("userId")) : null;
    }

}

