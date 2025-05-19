package org.quack.QUACKServer.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.domain
 * @fileName : CustomerUserMetadata
 * @date : 25. 5. 11.
 */

@Entity
@Table(name ="customer_user_metadata")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerUserMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_user_metadata_id", nullable = false)
    private Long customerUserMetadataId;

    @Column(name = "customer_user_id")
    private Long customerUserId;

    @Column(name = "profile_image_id")
    private Long profileImageId;

    @Column(name = "terms_agreed")
    private Boolean termsAgreed;

    @Column(name = "privacy_agreed")
    private Boolean privacyAgreed;

    @Column(name = "location_terms_agreed")
    private Boolean locationTermsAgreed;

    @Column(name = "decibel")
    private Long decibel;
}
