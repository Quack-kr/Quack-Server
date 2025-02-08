package org.quack.QUACKServer.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserInfoRequest {
    private String nickname;
    private String profileImage;
}
