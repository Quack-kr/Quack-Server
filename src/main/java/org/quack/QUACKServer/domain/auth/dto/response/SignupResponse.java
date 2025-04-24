package org.quack.QUACKServer.domain.auth.dto.response;

import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.response
 * @fileName : SignupResponse
 * @date : 25. 4. 21.
 */
@Builder
public record SignupResponse(
        String accessToken,
        String refreshToken
) {
}
