package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "review_keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReviewKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewKeywordId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    private ReviewKeyword(Review review, Keyword keyword) {
        this.review = review;
        this.keyword = keyword;
    }

    public static ReviewKeyword create(Review review, Keyword keyword) {
        return new ReviewKeyword(review, keyword);
    }


    //TODO: 연관관계 편의메서드 작성하기
}
