package org.quack.QUACKServer.dto.user;

import org.quack.QUACKServer.validation.NicknameConstraint;

public record RegisterUserRequest(
        @NicknameConstraint
        String nickname
)
{
    public static RegisterUserRequest from(String nickname) {
        return new RegisterUserRequest(nickname);
    }
}
