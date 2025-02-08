package org.quack.QUACKServer.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    private String keywordName;  // '맛있어요', '친절해요' 등
    private String keywordType;  // 긍정, 부정 -> enum 타입으로 변경
    private LocalDateTime createdDate;
}
