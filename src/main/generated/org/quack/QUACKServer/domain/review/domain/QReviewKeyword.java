package org.quack.QUACKServer.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewKeyword is a Querydsl query type for ReviewKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewKeyword extends EntityPathBase<ReviewKeyword> {

    private static final long serialVersionUID = -1183505268L;

    public static final QReviewKeyword reviewKeyword = new QReviewKeyword("reviewKeyword");

    public final NumberPath<Long> reviewKeywordId = createNumber("reviewKeywordId", Long.class);

    public final EnumPath<org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag> reviewTag = createEnum("reviewTag", org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewTag.class);

    public final EnumPath<org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType> reviewType = createEnum("reviewType", org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType.class);

    public QReviewKeyword(String variable) {
        super(ReviewKeyword.class, forVariable(variable));
    }

    public QReviewKeyword(Path<? extends ReviewKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewKeyword(PathMetadata metadata) {
        super(ReviewKeyword.class, metadata);
    }

}

