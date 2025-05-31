package org.quack.QUACKServer.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNicknameSequence is a Querydsl query type for NicknameSequence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNicknameSequence extends EntityPathBase<NicknameSequence> {

    private static final long serialVersionUID = 43171591L;

    public static final QNicknameSequence nicknameSequence = new QNicknameSequence("nicknameSequence");

    public final EnumPath<org.quack.QUACKServer.domain.auth.enums.AuthEnum.NicknameColorPrefix> colorPrefix = createEnum("colorPrefix", org.quack.QUACKServer.domain.auth.enums.AuthEnum.NicknameColorPrefix.class);

    public final EnumPath<org.quack.QUACKServer.domain.auth.enums.AuthEnum.NicknameMenuPrefix> menuPrefix = createEnum("menuPrefix", org.quack.QUACKServer.domain.auth.enums.AuthEnum.NicknameMenuPrefix.class);

    public final NumberPath<Long> nicknameSequenceId = createNumber("nicknameSequenceId", Long.class);

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QNicknameSequence(String variable) {
        super(NicknameSequence.class, forVariable(variable));
    }

    public QNicknameSequence(Path<? extends NicknameSequence> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNicknameSequence(PathMetadata metadata) {
        super(NicknameSequence.class, metadata);
    }

}

