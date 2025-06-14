package org.quack.QUACKServer.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.auth.enums.AuthEnum;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : CustomerUserNicknameSequence
 * @date : 25. 5. 31.
 */
@Entity
@Table(name ="customer_user_nickname_sequence")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerUserNicknameSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nickname_sequence_id", nullable = false)
    private Long nicknameSequenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "nickname_color_prefix", nullable = false)
    private AuthEnum.NicknameColorPrefix colorPrefix;

    @Enumerated(EnumType.STRING)
    @Column(name = "nickname_menu_prefix", nullable = false)
    private AuthEnum.NicknameMenuPrefix menuPrefix;

    @Column(name = "sequence", nullable = false)
    private Long sequence;

    @Column(name = "create_at")
    private LocalDateTime updateAt;


    @Builder(builderMethodName = "createBuilder")
    private CustomerUserNicknameSequence(AuthEnum.NicknameColorPrefix colorPrefix,
                                         AuthEnum.NicknameMenuPrefix menuPrefix) {
        this.colorPrefix = colorPrefix;
        this.menuPrefix = menuPrefix;
        this.sequence = 1L;
        this.updateAt = LocalDateTime.now();
    }

    public void increase() {
        this.sequence += 1;
        this.updateAt = LocalDateTime.now();
    }

}
