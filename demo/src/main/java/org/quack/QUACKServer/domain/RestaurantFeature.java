package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_feature")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantFeatureId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private String featureCategory; // ex) "주차여부" 등 -> 고정된 값을 가지고 있어야 할 듯. Enum 사용 ?
    private String featureValue;    // ex) "주차장 먼곳"


    // TODO: featureCategory 구현 방식, featureCategory 중 '분위기' 관련 구현 방식, 생성자 및 연관관계 편의 메서드 구현
}
