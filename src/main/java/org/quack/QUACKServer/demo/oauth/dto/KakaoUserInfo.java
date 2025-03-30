package org.quack.QUACKServer.demo.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfo(
        @JsonProperty("id") Long socialId,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public String email() {
        return kakaoAccount.email();
    }

    public record KakaoAccount(String email){}
}
