package org.quack.QUACKServer.demo.dto.user;

import org.quack.QUACKServer.demo.validation.NicknameConstraint;

public record RegisterUserRequest(
        @NicknameConstraint
        String nickname
)
{
    public static RegisterUserRequest from(String nickname) {
        return new RegisterUserRequest(nickname);
    }
}
