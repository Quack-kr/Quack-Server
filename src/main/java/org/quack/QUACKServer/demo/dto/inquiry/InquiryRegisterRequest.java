package org.quack.QUACKServer.demo.dto.inquiry;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InquiryRegisterRequest(
        @NotNull(message = "제목은 필수사항입니다.")
        @Size(max=150, message="제목은 최대 150자까지 작성가능합니다.")
        String title,
        @NotNull(message = "내용은 필수사항입니다.")
        @Size(max=300, message="내용은 최대 300자까지 작성가능합니다.")
        String content
) { }
