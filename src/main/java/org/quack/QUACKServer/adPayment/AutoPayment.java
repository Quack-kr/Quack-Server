package org.quack.QUACKServer.adPayment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.adPayment.domain
 * @fileName : AuthPayment
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "auto_payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutoPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_payments_id")
    private Long id;

    @Column(name = "payment_log_id")
    private Long paymentLogId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

