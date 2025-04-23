package org.quack.QUACKServer.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.quack.QUACKServer.global.security.enums.ClientType;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.request
 * @fileName : SignupRequest
 * @date : 25. 4. 21.
 */
public record SignupRequest(
        @NotBlank String nickname,
        @NotBlank ClientType clientType
) {
}
