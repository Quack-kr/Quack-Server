package org.quack.QUACKServer.dto.user;

public record UpdateUserResponse(String message, boolean isUpdate)
{
    public static UpdateUserResponse of(String message, boolean isUpdate) {
        return new UpdateUserResponse(message, isUpdate);
    }

}
