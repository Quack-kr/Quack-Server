package org.quack.QUACKServer.adPayment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAutoPayment is a Querydsl query type for AutoPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAutoPayment extends EntityPathBase<AutoPayment> {

    private static final long serialVersionUID = 992385363L;

    public static final QAutoPayment autoPayment = new QAutoPayment("autoPayment");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ownerId = createNumber("ownerId", Long.class);

    public final NumberPath<Long> paymentLogId = createNumber("paymentLogId", Long.class);

    public QAutoPayment(String variable) {
        super(AutoPayment.class, forVariable(variable));
    }

    public QAutoPayment(Path<? extends AutoPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAutoPayment(PathMetadata metadata) {
        super(AutoPayment.class, metadata);
    }

}

