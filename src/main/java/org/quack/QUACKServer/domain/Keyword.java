package org.quack.QUACKServer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.security.Key;
import java.time.LocalDateTime;
import org.quack.QUACKServer.domain.common.KeywordType;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    private String keywordName;  // '맛있어요', '친절해요' 등 -> 타입 고민해보기

    @Enumerated(EnumType.STRING)
    private KeywordType keywordType;


    public static Keyword create(String keywordName, KeywordType keywordType) {
        return Keyword.builder().keywordName(keywordName).keywordType(keywordType).build();
    }
//    private LocalDateTime createdDate;

    //TODO: 컬럼 별 제약 조건과 생성자 및 연관관계 편의 메서드 구현
}
