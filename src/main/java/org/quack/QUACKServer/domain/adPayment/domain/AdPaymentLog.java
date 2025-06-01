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
 * @fileName : AdPaymentLog
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "ad_payment_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdPaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_payment_logs_id")
    private Long id;

    @Column(name = "ad_payment_type_id")
    private Long adPaymentTypeId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "period_start")
    private LocalDateTime periodStart;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "pg_provider")
    private String pgProvider;

    @Column(name = "pg_data", columnDefinition = "json")
    private String pgData;

    @Column(name = "pg_id")
    private String pgId;

    @Column(name = "is_auto")
    private Boolean isAuto;

    @Column(name = "auto_payment_id")
    private Long autoPaymentId;

    @Column(name = "period_end")
    private LocalDateTime periodEnd;
}

