package org.quack.QUACKServer.oauth.dto;

public record KakaoUserInfo(
        Long socialId,
        String email
) { }
