package org.quack.QUACKServer.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerUser is a Querydsl query type for CustomerUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerUser extends EntityPathBase<CustomerUser> {

    private static final long serialVersionUID = -2133580631L;

    public static final QCustomerUser customerUser = new QCustomerUser("customerUser");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> customerUserId = createNumber("customerUserId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<org.quack.QUACKServer.core.security.enums.ProviderType> provider = createEnum("provider", org.quack.QUACKServer.core.security.enums.ProviderType.class);

    public final StringPath providerId = createString("providerId");

    public QCustomerUser(String variable) {
        super(CustomerUser.class, forVariable(variable));
    }

    public QCustomerUser(Path<? extends CustomerUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerUser(PathMetadata metadata) {
        super(CustomerUser.class, metadata);
    }

}

