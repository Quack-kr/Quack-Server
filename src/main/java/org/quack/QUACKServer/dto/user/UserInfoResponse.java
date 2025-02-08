package org.quack.QUACKServer.dto.user;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String nickname;
    private int reviewCount;
    private int savedRestaurantCount;
}
