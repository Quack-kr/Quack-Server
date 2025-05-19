package org.quack.QUACKServer.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerSavedRestaurant is a Querydsl query type for CustomerSavedRestaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerSavedRestaurant extends EntityPathBase<CustomerSavedRestaurant> {

    private static final long serialVersionUID = 20369838L;

    public static final QCustomerSavedRestaurant customerSavedRestaurant = new QCustomerSavedRestaurant("customerSavedRestaurant");

    public final NumberPath<Long> customerSavedRestaurantId = createNumber("customerSavedRestaurantId", Long.class);

    public final NumberPath<Long> customerUserId = createNumber("customerUserId", Long.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final BooleanPath status = createBoolean("status");

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QCustomerSavedRestaurant(String variable) {
        super(CustomerSavedRestaurant.class, forVariable(variable));
    }

    public QCustomerSavedRestaurant(Path<? extends CustomerSavedRestaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerSavedRestaurant(PathMetadata metadata) {
        super(CustomerSavedRestaurant.class, metadata);
    }

}

