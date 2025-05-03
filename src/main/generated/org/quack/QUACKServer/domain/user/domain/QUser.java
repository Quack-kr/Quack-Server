package org.quack.QUACKServer.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1085945725L;

    public static final QUser user = new QUser("user");

    public final org.quack.QUACKServer.domain.common.domain.QBaseEntity _super = new org.quack.QUACKServer.domain.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isSignUp = createBoolean("isSignUp");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<org.quack.QUACKServer.domain.auth.enums.Role> roleType = createEnum("roleType", org.quack.QUACKServer.domain.auth.enums.Role.class);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<org.quack.QUACKServer.global.security.enums.ClientType> socialType = createEnum("socialType", org.quack.QUACKServer.global.security.enums.ClientType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

