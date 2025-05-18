package org.quack.QUACKServer.domain.menu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuCategory is a Querydsl query type for MenuCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuCategory extends EntityPathBase<MenuCategory> {

    private static final long serialVersionUID = -370236471L;

    public static final QMenuCategory menuCategory = new QMenuCategory("menuCategory");

    public final StringPath categoryName = createString("categoryName");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> menuCategoryId = createNumber("menuCategoryId", Long.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public QMenuCategory(String variable) {
        super(MenuCategory.class, forVariable(variable));
    }

    public QMenuCategory(Path<? extends MenuCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuCategory(PathMetadata metadata) {
        super(MenuCategory.class, metadata);
    }

}

