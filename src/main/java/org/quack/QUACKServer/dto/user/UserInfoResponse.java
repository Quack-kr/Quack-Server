package org.quack.QUACKServer.dto.user;



public record UserInfoResponse(String nickname,
                              int reviewCount,
                              int savedRestaurantCount)
{
    public static UserInfoResponse of(String nickname, int reviewCount, int savedRestaurantCount) {
        return new UserInfoResponse(nickname, reviewCount, savedRestaurantCount);
    }
}
