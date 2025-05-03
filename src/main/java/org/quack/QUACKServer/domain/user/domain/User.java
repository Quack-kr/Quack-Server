package org.quack.QUACKServer.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.quack.QUACKServer.domain.common.domain.BaseEntity;
import org.quack.QUACKServer.domain.auth.enums.Role;
import org.quack.QUACKServer.global.security.enums.ClientType;

import java.time.LocalDateTime;


@Entity
@Table(name = "customer_user",
        uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_SOCIAL_ID_CONSTRAINT",
        columnNames = "social_id"),
        @UniqueConstraint(name = "UNIQUE_NICKNAME_CONSTRAINT",
        columnNames = "nickname")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@SQLRestriction("is_deleted = false AND deleted_at is NULL")
@SQLDelete(sql = "UPDATE customer_user SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType socialType;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true, length = 20)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role roleType;

    @Column(nullable = false)
    private String profileImage;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isSignUp;

    @Builder(access = AccessLevel.PRIVATE, builderMethodName = "createBuilder")
    private User(ClientType socialType, String socialId, String email, String nickname, String profileImage, boolean isSignUp){
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.roleType = Role.USER;
        this.profileImage = profileImage;
        this.isDeleted = false;
        this.deletedAt = null;
        this.isSignUp = isSignUp;
    }

    public static User createBySocial(ClientType socialType,
                               String socialId, String email,
                               String nickname, String profileImage, boolean isSignUp) {
        return User.createBuilder()
                .socialType(socialType)
                .socialId(socialId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .isSignUp(isSignUp)
                .build();
    }

    public void registerUser(String nickname) {
        this.isSignUp = true;
        this.nickname = nickname;
    }


//    public void updateUserProfile (UpdateUserInfoRequest updateUserInfoRequest) {
//        if (updateUserInfoRequest.nickname() != null) {
//            this.nickname = updateUserInfoRequest.nickname();
//        }
//
//        if (updateUserInfoRequest.profileImage() != null) {
//            this.profileImage = updateUserInfoRequest.profileImage();
//        }
//    }
//

}
