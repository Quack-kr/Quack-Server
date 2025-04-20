package org.quack.QUACKServer.domain.auth.dto;

import lombok.Builder;
import org.quack.QUACKServer.domain.auth.enums.SignUpStatus;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto
 * @fileName : LoginResponse
 * @date : 25. 4. 20.
 */
@Builder
public record LoginResponse(
        Long userId,
        SignUpStatus signUpStatus,
        String email
) {


}
