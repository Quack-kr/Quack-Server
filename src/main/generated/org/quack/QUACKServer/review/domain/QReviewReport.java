package org.quack.QUACKServer.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewReport is a Querydsl query type for ReviewReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewReport extends EntityPathBase<ReviewReport> {

    private static final long serialVersionUID = -41745575L;

    public static final QReviewReport reviewReport = new QReviewReport("reviewReport");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isReported = createBoolean("isReported");

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public QReviewReport(String variable) {
        super(ReviewReport.class, forVariable(variable));
    }

    public QReviewReport(Path<? extends ReviewReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewReport(PathMetadata metadata) {
        super(ReviewReport.class, metadata);
    }

}

