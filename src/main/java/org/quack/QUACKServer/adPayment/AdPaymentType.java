package org.quack.QUACKServer.adPayment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.adPayment.domain
 * @fileName : AdPaymentType
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "ad_payment_types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdPaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_payment_types_id")
    private Long id;

    private String type;
    private String name;

    @Column(unique = true)
    private String keyword;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_sold_out")
    private Boolean isSoldOut;

    private String price;

    @Column(name = "discount_rate")
    private String discountRate;

    @Column(name = "description_off", columnDefinition = "json")
    private String descriptionOff;

    @Column(name = "description_on", columnDefinition = "json")
    private String descriptionOn;

    @Column(name = "top_text")
    private String topText;

    @Column(name = "bottom_text")
    private String bottomText;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "payload_schema", columnDefinition = "json")
    private String payloadSchema;
}

