package org.quack.QUACKServer.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.domain
 * @fileName : BusinessUser
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "business_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_user_id")
    private Long id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;
}

