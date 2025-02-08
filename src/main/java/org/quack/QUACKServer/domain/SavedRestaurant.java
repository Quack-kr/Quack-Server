package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "saved_restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class SavedRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savedId;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 저장한 유저

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant; // 저장한 가게

    private LocalDateTime createdDate;
}