package org.quack.QUACKServer.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.quack.QUACKServer.demo.domain.common.KeywordType;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    private String keywordName;  // '맛있어요', '친절해요' 등 -> 타입 고민해보기

    @Enumerated(EnumType.STRING)
    private KeywordType keywordType;

    private LocalDateTime createdDate;

    //TODO: 컬럼 별 제약 조건과 생성자 및 연관관계 편의 메서드 구현
}
