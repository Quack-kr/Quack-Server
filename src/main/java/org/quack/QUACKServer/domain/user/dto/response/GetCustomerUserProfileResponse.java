package org.quack.QUACKServer.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.dto
 * @fileName : GetCustomerUserProfileResponse
 * @date : 25. 5. 25.
 */
@Builder(access = AccessLevel.PRIVATE)
public record GetCustomerUserProfileResponse(
        String nickname,
        Long profilePhotosId
) {

    public static GetCustomerUserProfileResponse of(CustomerUser user, CustomerUserMetadata metadata) {
        return GetCustomerUserProfileResponse.builder()
                .nickname(user.getNickname())
                .profilePhotosId(metadata.getProfileImageId())
                .build();
    }
}
