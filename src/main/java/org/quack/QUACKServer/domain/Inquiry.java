package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;
    private String replyStatus;  // enum 타입으로 변경
    private LocalDateTime createdDate;

    // 1:1 관계 (문의 -> 답변)
    @OneToOne(mappedBy = "inquiry", cascade = CascadeType.ALL, fetch = LAZY)
    private InquiryReply inquiryReply;
}
