package org.quack.QUACKServer.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.quack.QUACKServer.global.security.enums.ProviderType;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.request
 * @fileName : SignupRequest
 * @date : 25. 4. 21.
 */
public record SignupRequest(
        @NotBlank String nickname,
        @NotNull ProviderType providerType,
        Boolean termsAgreed,
        Boolean privacyAgreed,
        Boolean locationTermsAgreed
) {
}
