package org.quack.QUACKServer.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public class NicknameRequest {

    @NotBlank
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
