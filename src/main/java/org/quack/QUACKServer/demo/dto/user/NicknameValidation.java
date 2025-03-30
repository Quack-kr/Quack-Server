package org.quack.QUACKServer.demo.dto.user;

public record NicknameValidation(
        boolean isAvailable
) {
    public static NicknameValidation from(boolean isAvailable) {
        return new NicknameValidation(isAvailable);
    }
}
