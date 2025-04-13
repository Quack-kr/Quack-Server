package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import org.quack.QUACKServer.domain.common.DayOfWeek;

@Entity
@Table(name = "restaurant_operating")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantOperating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operatingId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;


    private String startTime;
    private String endTime;
    private String breakStartTime;
    private String breakEndTime;
    private String lastOrderTime;
    private Boolean isHoliday;

    // TODO: 생성자 및 연관관계 편의메서드 작성하기
}
