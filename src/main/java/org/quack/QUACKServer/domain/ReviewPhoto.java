package org.quack.QUACKServer.domain;


import static jakarta.persistence.FetchType.*;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_photo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPhotoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;  // 어떤 리뷰에 달린 사진인지

    private String photoUrl;
    private LocalDateTime createdDate;
}
