package org.quack.QUACKServer.domain.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review_image")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;

    @Column(name = "review_image_path", nullable = false)
    private String reviewImagePath;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
