package org.quack.QUACKServer.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.quack.QUACKServer.global.security.enums.ProviderType;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.domain
 * @fileName : CustomerUser
 * @date : 25. 5. 11.
 */
@Entity
@Table(name = "customer_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_SOCIAL_ID_CONSTRAINT",
                        columnNames = "provider_id"),
                @UniqueConstraint(name = "UNIQUE_NICKNAME_CONSTRAINT",
                        columnNames = "nickname")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@SQLRestriction("is_deleted = false AND deleted_at is NULL")
@SQLDelete(sql = "UPDATE customer_user SET is_deleted = true, deleted_at = NOW() WHERE customer_user_id = ?")
public class CustomerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_user_id", nullable = false)
    private Long customerUserId;

    @Enumerated
    @Column(name = "provider" , nullable = false)
    private ProviderType provider;

    @Column(name = "provider_id" , nullable = false)
    private String providerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = true, length = 20)
    private String nickname;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;


    @Builder(access = AccessLevel.PRIVATE, builderMethodName = "createBuilder")
    private CustomerUser(ProviderType provider, String providerId, String email, String nickname){
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickname = nickname;
        this.isDeleted = false;
        this.deletedAt = null;
        this.createAt = LocalDateTime.now();
    }

    public static CustomerUser createBySocial(ProviderType provider, String providerId, String email, String nickname) {
        return CustomerUser.createBuilder()
                .provider(provider)
                .providerId(providerId)
                .email(email)
                .nickname(nickname)
                .build();
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }


}
