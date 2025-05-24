package org.quack.QUACKServer.domain.photos;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import org.quack.QUACKServer.domain.photos.domain.Photos;


/**
 * QPhotos is a Querydsl query type for Photos
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhotos extends EntityPathBase<Photos> {

    private static final long serialVersionUID = -1561626371L;

    public static final QPhotos photos = new QPhotos("photos");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Long> photosId = createNumber("photosId", Long.class);

    public final StringPath photoType = createString("photoType");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final NumberPath<Long> targetId = createNumber("targetId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPhotos(String variable) {
        super(Photos.class, forVariable(variable));
    }

    public QPhotos(Path<? extends Photos> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhotos(PathMetadata metadata) {
        super(Photos.class, metadata);
    }

}

