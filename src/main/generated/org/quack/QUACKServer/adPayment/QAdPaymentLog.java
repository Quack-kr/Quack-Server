package org.quack.QUACKServer.adPayment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdPaymentLog is a Querydsl query type for AdPaymentLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdPaymentLog extends EntityPathBase<AdPaymentLog> {

    private static final long serialVersionUID = -868668571L;

    public static final QAdPaymentLog adPaymentLog = new QAdPaymentLog("adPaymentLog");

    public final NumberPath<Long> adPaymentTypeId = createNumber("adPaymentTypeId", Long.class);

    public final NumberPath<Long> autoPaymentId = createNumber("autoPaymentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAuto = createBoolean("isAuto");

    public final DateTimePath<java.time.LocalDateTime> paidAt = createDateTime("paidAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> periodEnd = createDateTime("periodEnd", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> periodStart = createDateTime("periodStart", java.time.LocalDateTime.class);

    public final StringPath pgData = createString("pgData");

    public final StringPath pgId = createString("pgId");

    public final StringPath pgProvider = createString("pgProvider");

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public QAdPaymentLog(String variable) {
        super(AdPaymentLog.class, forVariable(variable));
    }

    public QAdPaymentLog(Path<? extends AdPaymentLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdPaymentLog(PathMetadata metadata) {
        super(AdPaymentLog.class, metadata);
    }

}

