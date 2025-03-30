package org.quack.QUACKServer.demo.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry_reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
public class InquiryReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @OneToOne(mappedBy = "inquiryReply", fetch = LAZY)
    private Inquiry inquiry;

    @Column
    private String content;

    private LocalDateTime createdDate;

    private InquiryReply(Inquiry inquiry, String content, LocalDateTime createdDate) {
        this.inquiry = inquiry;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static InquiryReply createInquiryReply(Inquiry inquiry, String content, LocalDateTime createdDate) {
        return new InquiryReply(inquiry, content, createdDate);
    }
}
