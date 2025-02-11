package org.quack.QUACKServer.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.quack.QUACKServer.domain.common.BaseEntity;
import org.quack.QUACKServer.domain.common.Role;
import org.quack.QUACKServer.domain.common.SocialType;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;


@Entity
@Table(name = "user",
        uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_SOCIAL_ID_CONSTRAINT",
        columnNames = "social_id"),
        @UniqueConstraint(name = "UNIQUE_NICKNAME_CONSTRAINT",
        columnNames = "nickname")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role roleType;

    @Column(nullable = false)
    private String profileImage;

    private User(SocialType socialType, String socialId, String email, String nickname, String profileImage){
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.roleType = Role.USER;
        this.profileImage = profileImage;
    }

    @Builder
    public static User createBySocial(SocialType socialType,
                               String socialId, String email,
                               String nickname, String profileImage) {
        return new User(socialType, socialId, email, nickname, profileImage);
    }


    public void updateUserProfile (UpdateUserInfoRequest updateUserInfoRequest) {
        if (updateUserInfoRequest.getNickname() != null) {
            this.nickname = updateUserInfoRequest.getNickname();
        }

        if (updateUserInfoRequest.getProfileImage() != null) {
            this.profileImage = updateUserInfoRequest.getProfileImage();
        }
    }

}
