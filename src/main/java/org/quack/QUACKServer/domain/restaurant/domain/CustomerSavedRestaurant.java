package org.quack.QUACKServer.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.user.domain.User;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description : 회원별 식당 관련 정보 매핑 엔티티
 * @packageName : org.quack.QUACKServer.domain.restaurant.domain
 * @fileName : CustomerSavedRestaurant
 * @date : 25. 5. 4.
 */

@Entity
@Table(name = "customer_saved_restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerSavedRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerSavedRestaurantId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private LocalDateTime createdAt = LocalDateTime.now();

}

