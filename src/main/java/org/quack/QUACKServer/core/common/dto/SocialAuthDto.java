package org.quack.QUACKServer.core.common.dto;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : SocialAuthDto
 * @date : 25. 4. 20.
 */
@Getter
@Builder
public class SocialAuthDto {

    private String providerId;
    private String email;

    public static SocialAuthDto from(Claims claims) {
        return SocialAuthDto.builder()
                .providerId(claims.getSubject()) // Social_id
                .email(claims.get("email", String.class))
                .build();
    }
}
