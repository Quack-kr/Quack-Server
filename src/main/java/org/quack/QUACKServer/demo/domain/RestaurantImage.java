package org.quack.QUACKServer.demo.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantImageId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private String imagePath;

    private LocalDateTime createdDate;

    // TODO: 생성자 및 연관관계 편의 메서드 구현
}
