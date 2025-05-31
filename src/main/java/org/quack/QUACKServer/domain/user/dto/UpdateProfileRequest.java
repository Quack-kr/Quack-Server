package org.quack.QUACKServer.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.dto
 * @fileName : UpdateProfileRequest
 * @date : 25. 5. 31.
 */
public record UpdateProfileRequest(
        Long profileImageId,
        @NotBlank String nickname
) {
}
