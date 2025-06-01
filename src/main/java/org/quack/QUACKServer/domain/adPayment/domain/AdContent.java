package org.quack.QUACKServer.domain.adPayment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.adPayment.domain
 * @fileName : AdContent
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "ad_contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_contents_id")
    private Long id;

    @Column(name = "ad_payment_type_id")
    private Long adPaymentTypeId;

    @Column(name = "period_start")
    private LocalDateTime periodStart;

    @Column(name = "period_end")
    private LocalDateTime periodEnd;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "ad_title_1")
    private String adTitle1;

    @Column(name = "ad_title_2")
    private String adTitle2;

    @Column(name = "user_description", columnDefinition = "text")
    private String userDescription;

    @Column(name = "menu_json", columnDefinition = "json")
    private String menuJson;

    @Column(name = "region_id", columnDefinition = "json")
    private String regionId;

    @Column(name = "region_name", columnDefinition = "json")
    private String regionName;
}

