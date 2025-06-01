package org.quack.QUACKServer.adPayment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdContent is a Querydsl query type for AdContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdContent extends EntityPathBase<AdContent> {

    private static final long serialVersionUID = -567815726L;

    public static final QAdContent adContent = new QAdContent("adContent");

    public final NumberPath<Long> adPaymentTypeId = createNumber("adPaymentTypeId", Long.class);

    public final StringPath adTitle1 = createString("adTitle1");

    public final StringPath adTitle2 = createString("adTitle2");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menuJson = createString("menuJson");

    public final DateTimePath<java.time.LocalDateTime> periodEnd = createDateTime("periodEnd", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> periodStart = createDateTime("periodStart", java.time.LocalDateTime.class);

    public final StringPath regionId = createString("regionId");

    public final StringPath regionName = createString("regionName");

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final StringPath userDescription = createString("userDescription");

    public QAdContent(String variable) {
        super(AdContent.class, forVariable(variable));
    }

    public QAdContent(Path<? extends AdContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdContent(PathMetadata metadata) {
        super(AdContent.class, metadata);
    }

}

