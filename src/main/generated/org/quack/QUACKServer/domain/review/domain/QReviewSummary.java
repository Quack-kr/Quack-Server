package org.quack.QUACKServer.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewSummary is a Querydsl query type for ReviewSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewSummary extends EntityPathBase<ReviewSummary> {

    private static final long serialVersionUID = 2068229705L;

    public static final QReviewSummary reviewSummary = new QReviewSummary("reviewSummary");

    public final NumberPath<Long> count = createNumber("count", Long.class);

    public final DateTimePath<java.time.LocalDateTime> historyDate = createDateTime("historyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> rank = createNumber("rank", Long.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final EnumPath<org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType> reviewKeywordType = createEnum("reviewKeywordType", org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType.class);

    public final NumberPath<Long> reviewSummaryId = createNumber("reviewSummaryId", Long.class);

    public final EnumPath<org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag> reviewTag = createEnum("reviewTag", org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag.class);

    public QReviewSummary(String variable) {
        super(ReviewSummary.class, forVariable(variable));
    }

    public QReviewSummary(Path<? extends ReviewSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewSummary(PathMetadata metadata) {
        super(ReviewSummary.class, metadata);
    }

}

