package org.quack.QUACKServer.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.domain
 * @fileName : CustomerSavedRestaurant
 * @date : 25. 5. 18.
 */

@Entity
@Table(
        name = "customer_saved_restaurant",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"customer_user_id", "restaurant_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerSavedRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_saved_restaurant_id", nullable = false)
    private Long customerSavedRestaurantId;

    @Column(name = "customer_user_id", nullable = false)
    private Long customerUserId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "update_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateAt;

    @Column(name = "status", columnDefinition = "1")
    private Boolean status;
}
