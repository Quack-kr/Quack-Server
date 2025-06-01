package org.quack.QUACKServer.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBusinessUserApply is a Querydsl query type for BusinessUserApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessUserApply extends EntityPathBase<BusinessUserApply> {

    private static final long serialVersionUID = 1102902475L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBusinessUserApply businessUserApply = new QBusinessUserApply("businessUserApply");

    public final StringPath businessLicenseUrl = createString("businessLicenseUrl");

    public final QBusinessUser businessUser;

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ownerName = createString("ownerName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DateTimePath<java.time.LocalDateTime> privacyAgreedAt = createDateTime("privacyAgreedAt", java.time.LocalDateTime.class);

    public final StringPath registrationNumber = createString("registrationNumber");

    public final EnumPath<BusinessUserApplyStatus> status = createEnum("status", BusinessUserApplyStatus.class);

    public final StringPath storeName = createString("storeName");

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QBusinessUserApply(String variable) {
        this(BusinessUserApply.class, forVariable(variable), INITS);
    }

    public QBusinessUserApply(Path<? extends BusinessUserApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBusinessUserApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBusinessUserApply(PathMetadata metadata, PathInits inits) {
        this(BusinessUserApply.class, metadata, inits);
    }

    public QBusinessUserApply(Class<? extends BusinessUserApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.businessUser = inits.isInitialized("businessUser") ? new QBusinessUser(forProperty("businessUser")) : null;
    }

}

