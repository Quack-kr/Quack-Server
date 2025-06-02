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

    @Column(nullable = false)
    private String menuName;


    private ReviewMenu(Review review, String menuName) {
        this.review = review;
        this.menuName = menuName;
    }

    public static ReviewMenu create(Review review, String menuName) {
        return new ReviewMenu(review, menuName);
    }

    // TODO: 연관관계 편의메서드 작성하기
}
