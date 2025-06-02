package org.quack.QUACKServer.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerUserNicknameSequence is a Querydsl query type for CustomerUserNicknameSequence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerUserNicknameSequence extends EntityPathBase<CustomerUserNicknameSequence> {

    private static final long serialVersionUID = -1430967016L;

    public static final QCustomerUserNicknameSequence customerUserNicknameSequence = new QCustomerUserNicknameSequence("customerUserNicknameSequence");

    public final EnumPath<org.quack.QUACKServer.auth.enums.AuthEnum.NicknameColorPrefix> colorPrefix = createEnum("colorPrefix", org.quack.QUACKServer.auth.enums.AuthEnum.NicknameColorPrefix.class);

    public final EnumPath<org.quack.QUACKServer.auth.enums.AuthEnum.NicknameMenuPrefix> menuPrefix = createEnum("menuPrefix", org.quack.QUACKServer.auth.enums.AuthEnum.NicknameMenuPrefix.class);

    public final NumberPath<Long> nicknameSequenceId = createNumber("nicknameSequenceId", Long.class);

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QCustomerUserNicknameSequence(String variable) {
        super(CustomerUserNicknameSequence.class, forVariable(variable));
    }

    public QCustomerUserNicknameSequence(Path<? extends CustomerUserNicknameSequence> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerUserNicknameSequence(PathMetadata metadata) {
        super(CustomerUserNicknameSequence.class, metadata);
    }

}

