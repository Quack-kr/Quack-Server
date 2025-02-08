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
// 생성자 선언 후, @Builder 붙이기
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;  // 어떤 가게에 대한 리뷰인지

    private String content;

    // == 연관관계 == //
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewKeyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReviewMenu> reviewMenus = new ArrayList<>();
}
