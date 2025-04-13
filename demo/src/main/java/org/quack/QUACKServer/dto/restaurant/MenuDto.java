package org.quack.QUACKServer.dto.restaurant;

public record MenuDto(
        String menuName,
        String menuDescription,
        int price,
        String menuImage)
{ }
