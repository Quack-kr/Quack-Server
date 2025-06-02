package org.quack.QUACKServer.domain;


import static jakarta.persistence.FetchType.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_photo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPhotoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false)
    private String photoUrl;


    private ReviewPhoto(Review review, String photoUrl){
        this.review = review;
        this.photoUrl = photoUrl;
    }

    public static ReviewPhoto create(Review review, String photoUrl){
        return new ReviewPhoto(review, photoUrl);
    }

    // TODO: 연관관계 편의메서드 작성하기
}
