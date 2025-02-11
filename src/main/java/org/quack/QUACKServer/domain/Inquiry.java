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
    private Enum replyStatus;


    private LocalDateTime createdDate;

    // 1:1 관계 (문의 -> 답변)
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inquiry_id")
    private InquiryReply inquiryReply;

    private Inquiry(User user, String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
        this.replyStatus = ReplyStatus.READY;
        this.createdDate = LocalDateTime.now();
    }

    public static Inquiry create(User user, String title, String content){
        return new Inquiry(user, title, content);
    }
}
