package org.quack.QUACKServer.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerUserMetadata is a Querydsl query type for CustomerUserMetadata
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerUserMetadata extends EntityPathBase<CustomerUserMetadata> {

    private static final long serialVersionUID = -693810440L;

    public static final QCustomerUserMetadata customerUserMetadata = new QCustomerUserMetadata("customerUserMetadata");

    public final NumberPath<Long> customerUserId = createNumber("customerUserId", Long.class);

    public final NumberPath<Long> customerUserMetadataId = createNumber("customerUserMetadataId", Long.class);

    public final NumberPath<Long> decibel = createNumber("decibel", Long.class);

    public final BooleanPath locationTermsAgreed = createBoolean("locationTermsAgreed");

    public final BooleanPath privacyAgreed = createBoolean("privacyAgreed");

    public final NumberPath<Long> profileImageId = createNumber("profileImageId", Long.class);

    public final BooleanPath termsAgreed = createBoolean("termsAgreed");

    public QCustomerUserMetadata(String variable) {
        super(CustomerUserMetadata.class, forVariable(variable));
    }

    public QCustomerUserMetadata(Path<? extends CustomerUserMetadata> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerUserMetadata(PathMetadata metadata) {
        super(CustomerUserMetadata.class, metadata);
    }

}

