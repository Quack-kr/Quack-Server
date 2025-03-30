package org.quack.QUACKServer.demo.dto.restaurant;

public record MenuDto(
        String menuName,
        String menuDescription,
        int price,
        String menuImage)
{ }
