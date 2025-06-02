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
 * @fileName : BusinessUserApply
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "business_user_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessUserApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_user_apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_user_id")
    private BusinessUser businessUser;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    @Enumerated(EnumType.STRING)
    private BusinessUserApplyStatus status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "privacy_agreed_at")
    private LocalDateTime privacyAgreedAt;

    @Column(columnDefinition = "TEXT")
    private String comment;
}

