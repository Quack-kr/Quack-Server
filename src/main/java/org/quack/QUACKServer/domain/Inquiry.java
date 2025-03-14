package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.quack.QUACKServer.domain.common.ReplyStatus;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column // length 미정
    private String title;

    @Column // length 미정
    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'READY'")
    private ReplyStatus replyStatus;


    private LocalDateTime createdDate;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inquiry_id")
    private InquiryReply inquiryReply;

    private Inquiry(User user, String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
        this.replyStatus = ReplyStatus.READY;
        this.createdDate = LocalDateTime.now();
    }

    public static Inquiry createInquiry(User user, String title, String content){
        Inquiry inquiry = new Inquiry(user, title, content);
        InquiryReply reply = InquiryReply.createInquiryReply(inquiry,null, null);
        inquiry.setInquiryReply(reply);
        return inquiry;
    }

    private void setInquiryReply(InquiryReply reply){
        this.inquiryReply = reply;
    }
}
