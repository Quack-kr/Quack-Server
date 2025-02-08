package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_feature")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class RestaurantFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantFeatureId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String featureCategory; // ex) "주차여부" 등
    private String featureValue;    // ex) "주차장 먼곳"
}
