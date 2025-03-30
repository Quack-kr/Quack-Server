package org.quack.QUACKServer.demo.dto.user;

public record RegisterResponse(
        String message,
        boolean isRegister)
{
    public static RegisterResponse from(String message, boolean isRegister) {
        return new RegisterResponse(message, isRegister);
    }
}
