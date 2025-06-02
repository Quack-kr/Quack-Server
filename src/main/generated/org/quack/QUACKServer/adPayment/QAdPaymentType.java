package org.quack.QUACKServer.adPayment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdPaymentType is a Querydsl query type for AdPaymentType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdPaymentType extends EntityPathBase<AdPaymentType> {

    private static final long serialVersionUID = -1158673607L;

    public static final QAdPaymentType adPaymentType = new QAdPaymentType("adPaymentType");

    public final StringPath bottomText = createString("bottomText");

    public final StringPath descriptionOff = createString("descriptionOff");

    public final StringPath descriptionOn = createString("descriptionOn");

    public final StringPath discountRate = createString("discountRate");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Integer> durationDays = createNumber("durationDays", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSoldOut = createBoolean("isSoldOut");

    public final StringPath keyword = createString("keyword");

    public final StringPath name = createString("name");

    public final StringPath payloadSchema = createString("payloadSchema");

    public final StringPath price = createString("price");

    public final StringPath topText = createString("topText");

    public final StringPath type = createString("type");

    public QAdPaymentType(String variable) {
        super(AdPaymentType.class, forVariable(variable));
    }

    public QAdPaymentType(Path<? extends AdPaymentType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdPaymentType(PathMetadata metadata) {
        super(AdPaymentType.class, metadata);
    }

}

