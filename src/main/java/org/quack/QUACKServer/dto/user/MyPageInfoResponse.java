package org.quack.QUACKServer.dto.user;


import lombok.Builder;

public record MyPageInfoResponse(String nickname,
                               String profileUrl,
                               int reviewCount,
                               int savedRestaurantCount,
                               int empathyDecibel)
{

    public static MyPageInfoResponse of(String nickname, String profileUrl, int reviewCount, int savedRestaurantCount,
                               int empathyDecibel) {
        return new MyPageInfoResponse(nickname, profileUrl, reviewCount, savedRestaurantCount, empathyDecibel);
    }

    public static MyPageInfoResponse defaultInfo() {
        return new MyPageInfoResponse(null, null, 0, 0, 0);
    }
}
