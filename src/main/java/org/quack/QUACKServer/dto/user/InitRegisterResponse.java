package org.quack.QUACKServer.dto.user;

import org.quack.QUACKServer.domain.common.SocialType;

public record InitRegisterResponse(
        SocialType socialType,
        String email,
        String defaultNickname
) {
    public static InitRegisterResponse of(SocialType socialType, String email, String defaultNickname) {
        return new InitRegisterResponse(socialType, email, defaultNickname);
    }
}
