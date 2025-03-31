package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "review_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewMenuId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="menu_id", nullable = false)
    private Menu menu;

    @Column
    private String evaluation;

    private ReviewMenu(Review review, Menu menu) {
        this.review = review;
        this.menu = menu;
    }

    public static ReviewMenu create(Review review, Menu menu) {
        return new ReviewMenu(review, menu);
    }

    // TODO: 연관관계 편의메서드 작성하기
}
