package org.quack.QUACKServer.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.review.enums.ReviewEnum;

@Entity
@Table(name = "review_keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewKeywordId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "review_tag", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReviewEnum.ReviewTag reviewTag;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_type", nullable = false)
    private ReviewEnum.ReviewKeywordType reviewType;

}
