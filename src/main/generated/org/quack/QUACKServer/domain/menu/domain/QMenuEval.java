package org.quack.QUACKServer.domain.menu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuEval is a Querydsl query type for MenuEval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuEval extends EntityPathBase<MenuEval> {

    private static final long serialVersionUID = -383994649L;

    public static final QMenuEval menuEval = new QMenuEval("menuEval");

    public final NumberPath<Long> evalId = createNumber("evalId", Long.class);

    public final EnumPath<org.quack.QUACKServer.domain.menu.enums.MenuEnum.MenuEvalType> menuEvalType = createEnum("menuEvalType", org.quack.QUACKServer.domain.menu.enums.MenuEnum.MenuEvalType.class);

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public QMenuEval(String variable) {
        super(MenuEval.class, forVariable(variable));
    }

    public QMenuEval(Path<? extends MenuEval> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuEval(PathMetadata metadata) {
        super(MenuEval.class, metadata);
    }

}

