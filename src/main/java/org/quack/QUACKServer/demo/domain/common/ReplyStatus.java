package org.quack.QUACKServer.demo.domain.common;

public enum ReplyStatus {
    READY("답변 대기"),
    COMP("답변 완료");

    private String status;

    ReplyStatus(String status) {
        this.status = status;
    }
}
