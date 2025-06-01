package org.quack.QUACKServer.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewEvalSummary is a Querydsl query type for ReviewEvalSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEvalSummary extends EntityPathBase<ReviewEvalSummary> {

    private static final long serialVersionUID = 342509669L;

    public static final QReviewEvalSummary reviewEvalSummary = new QReviewEvalSummary("reviewEvalSummary");

    public final DatePath<java.time.LocalDate> historyAt = createDate("historyAt", java.time.LocalDate.class);

    public final EnumPath<org.quack.QUACKServer.review.enums.ReviewEnum.ReviewKeywordType> keywordType = createEnum("keywordType", org.quack.QUACKServer.review.enums.ReviewEnum.ReviewKeywordType.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Long> reviewEvalSummaryId = createNumber("reviewEvalSummaryId", Long.class);

    public final NumberPath<Integer> reviewSummaryRank = createNumber("reviewSummaryRank", Integer.class);

    public final EnumPath<org.quack.QUACKServer.review.enums.ReviewEnum.ReviewTag> reviewSummaryTag = createEnum("reviewSummaryTag", org.quack.QUACKServer.review.enums.ReviewEnum.ReviewTag.class);

    public final NumberPath<Long> totalCount = createNumber("totalCount", Long.class);

    public QReviewEvalSummary(String variable) {
        super(ReviewEvalSummary.class, forVariable(variable));
    }

    public QReviewEvalSummary(Path<? extends ReviewEvalSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewEvalSummary(PathMetadata metadata) {
        super(ReviewEvalSummary.class, metadata);
    }

}

