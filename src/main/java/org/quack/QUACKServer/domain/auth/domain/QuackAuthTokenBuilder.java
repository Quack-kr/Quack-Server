package org.quack.QUACKServer.domain.auth.domain;

import org.quack.QUACKServer.domain.auth.enums.TokenStatus;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : QuackAuthTokenBuilder
 * @date : 25. 6. 1.
 */
public class QuackAuthTokenBuilder {
    public static QuackAuthTokenValue build(QuackUser quackUser, String accessToken,
                                                  String refreshToken, TokenStatus tokenStatus) {
        return new QuackAuthTokenValue(quackUser, accessToken, refreshToken, tokenStatus, LocalDateTime.now());
    }

    public static QuackAuthTokenValue buildActive(QuackUser quackUser, String accessToken,
                                                        String refreshToken) {
        return build(quackUser, accessToken, refreshToken, TokenStatus.ACTIVE);
    }

    public static QuackAuthTokenValue buildInActive(QuackUser quackUser, String accessToken,
                                                          String refreshToken) {
        return build(quackUser, accessToken, refreshToken, TokenStatus.INACTIVE);
    }

    public static QuackAuthTokenValue updateRequestTime(QuackAuthTokenValue oldToken) {
        return build(oldToken.quackUser(), oldToken.accessToken(), oldToken.refreshToken(), oldToken.tokenStatus());
    }
}
