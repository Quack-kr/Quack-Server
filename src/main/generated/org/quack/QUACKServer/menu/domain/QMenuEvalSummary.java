package org.quack.QUACKServer.menu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuEvalSummary is a Querydsl query type for MenuEvalSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuEvalSummary extends EntityPathBase<MenuEvalSummary> {

    private static final long serialVersionUID = 1799264407L;

    public static final QMenuEvalSummary menuEvalSummary = new QMenuEvalSummary("menuEvalSummary");

    public final DatePath<java.time.LocalDate> historyAt = createDate("historyAt", java.time.LocalDate.class);

    public final NumberPath<Long> menuEvalSummaryId = createNumber("menuEvalSummaryId", Long.class);

    public final NumberPath<Integer> menuEvalSummaryRank = createNumber("menuEvalSummaryRank", Integer.class);

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final NumberPath<Long> totalCount = createNumber("totalCount", Long.class);

    public QMenuEvalSummary(String variable) {
        super(MenuEvalSummary.class, forVariable(variable));
    }

    public QMenuEvalSummary(Path<? extends MenuEvalSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuEvalSummary(PathMetadata metadata) {
        super(MenuEvalSummary.class, metadata);
    }

}

