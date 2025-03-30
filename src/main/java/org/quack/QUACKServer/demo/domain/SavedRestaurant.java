package org.quack.QUACKServer.demo.domain;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "saved_restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavedRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savedId;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;


    private SavedRestaurant(User user, Restaurant restaurant){
        this.user = user;
        this.restaurant =restaurant;
    }

    public static SavedRestaurant create(User user, Restaurant restaurant) {
        return new SavedRestaurant(user, restaurant);
    }

    //TODO: @JoinColumn 안하고, 단순히 user_id, restaurant_id로 저장하는 방식 생각해보기
}