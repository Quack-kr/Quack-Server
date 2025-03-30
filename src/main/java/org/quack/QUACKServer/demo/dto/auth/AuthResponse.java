package org.quack.QUACKServer.demo.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Boolean isNewUser
) {
    public static AuthResponse of(String accessToken, String refreshToken, boolean isNewUser) {
        return new AuthResponse(accessToken, refreshToken, isNewUser);
    }
}
