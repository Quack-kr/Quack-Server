package org.quack.QUACKServer.domain.auth.dto.response;

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
        SignUpStatus signUpStatus,
        String email,
        String nickname
) {

}