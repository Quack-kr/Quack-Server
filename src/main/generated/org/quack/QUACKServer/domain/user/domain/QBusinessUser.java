package org.quack.QUACKServer.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessUser is a Querydsl query type for BusinessUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessUser extends EntityPathBase<BusinessUser> {

    private static final long serialVersionUID = 160031843L;

    public static final QBusinessUser businessUser = new QBusinessUser("businessUser");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedTime = createDateTime("deletedTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath providerId = createString("providerId");

    public final StringPath providerName = createString("providerName");

    public QBusinessUser(String variable) {
        super(BusinessUser.class, forVariable(variable));
    }

    public QBusinessUser(Path<? extends BusinessUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessUser(PathMetadata metadata) {
        super(BusinessUser.class, metadata);
    }

}

