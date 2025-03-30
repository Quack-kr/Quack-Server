package org.quack.QUACKServer.demo.dto.inquiry;

import java.time.LocalDateTime;
import org.quack.QUACKServer.demo.domain.common.ReplyStatus;

public record InquiriesResponse(
        Long inquiryId,
        String title,
        String content,
        LocalDateTime createDate,
        String replyContent,
        LocalDateTime replyCreateDate,
        ReplyStatus replyStatus)
{ }
