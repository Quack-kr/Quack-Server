package org.quack.QUACKServer.demo.dto.auth;

public record ReissueResponse(String accessToken,
                              String refreshToken)
{
    public static ReissueResponse of(String accessToken, String refreshToken) {
        return new ReissueResponse(accessToken, refreshToken);
    }
}
