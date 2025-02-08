package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantImageId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String imagePath;        // 실제 파일 경로나 URL
    private LocalDateTime createdDate;
}
