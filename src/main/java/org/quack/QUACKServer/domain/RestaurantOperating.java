package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_operating")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class RestaurantOperating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantOperatingId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String dayOfWeek;   //  "MON", "TUE"
    private String startTime;   // "09:00"
    private String endTime;     // "22:00"
    private Boolean isHoliday;  // 휴무 여부
}
