package org.quack.QUACKServer.domain.menu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -2063049301L;

    public static final QMenu menu = new QMenu("menu");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> menuCategoryId = createNumber("menuCategoryId", Long.class);

    public final StringPath menuDescription = createString("menuDescription");

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final StringPath menuName = createString("menuName");

    public final NumberPath<Long> menuPrice = createNumber("menuPrice", Long.class);

    public final NumberPath<Integer> menuSortOrder = createNumber("menuSortOrder", Integer.class);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QMenu(String variable) {
        super(Menu.class, forVariable(variable));
    }

    public QMenu(Path<? extends Menu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenu(PathMetadata metadata) {
        super(Menu.class, metadata);
    }

}

