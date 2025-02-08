package org.quack.QUACKServer.oauth.dto;


import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private String email;
    private String nickname;
    private String profileImage;
    // 추후 필요하면 kakao id, gender 등 추가
}
