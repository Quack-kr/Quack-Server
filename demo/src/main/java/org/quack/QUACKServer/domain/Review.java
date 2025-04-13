package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import org.quack.QUACKServer.domain.common.BaseEntity;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;  // 어떤 가게에 대한 리뷰인지

    @Column(nullable = true, length = 150)
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewMenu> reviewMenus = new ArrayList<>();

    private Review(User user, Restaurant restaurant, String content) {
        this.user = user;
        this.restaurant = restaurant;
        this.content = content;
    }

    public static Review create(User user, Restaurant restaurant, String content) {
        return new Review(user, restaurant, content);
    }
}
