package org.quack.QUACKServer.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewReportHistory is a Querydsl query type for ReviewReportHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewReportHistory extends EntityPathBase<ReviewReportHistory> {

    private static final long serialVersionUID = -2142575325L;

    public static final QReviewReportHistory reviewReportHistory = new QReviewReportHistory("reviewReportHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isReported = createBoolean("isReported");

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public QReviewReportHistory(String variable) {
        super(ReviewReportHistory.class, forVariable(variable));
    }

    public QReviewReportHistory(Path<? extends ReviewReportHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewReportHistory(PathMetadata metadata) {
        super(ReviewReportHistory.class, metadata);
    }

}

