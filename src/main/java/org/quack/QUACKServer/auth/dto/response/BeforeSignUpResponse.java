package org.quack.QUACKServer.auth.dto.response;

import lombok.Builder;
import org.quack.QUACKServer.auth.enums.SignUpStatus;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto
 * @fileName : BeforeSignUpResponse
 * @date : 25. 4. 20.
 */
@Builder
public record BeforeSignUpResponse(
        SignUpStatus signUpStatus,
        String email,
        String nickname
) {

}