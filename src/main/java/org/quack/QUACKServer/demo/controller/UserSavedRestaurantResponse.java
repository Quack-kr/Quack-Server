package org.quack.QUACKServer.demo.controller;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.demo.domain.Restaurant;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record UserSavedRestaurantResponse() {

    public static UserSavedRestaurantResponse from(List<Restaurant> restaurants) {
        return UserSavedRestaurantResponse.builder().build();
    }
}
