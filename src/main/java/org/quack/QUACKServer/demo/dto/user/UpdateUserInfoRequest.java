package org.quack.QUACKServer.demo.dto.user;


public record UpdateUserInfoRequest(String nickname, String profileImage)
{
    public static UpdateUserInfoRequest of(String nickname, String profileImage) {
        return new UpdateUserInfoRequest(nickname, profileImage);
    }
}
