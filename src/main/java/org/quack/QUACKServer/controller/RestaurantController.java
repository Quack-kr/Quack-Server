package org.quack.QUACKServer.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.restaurant.RestaurantInfoResponse;
import org.quack.QUACKServer.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantInfoResponse> getRestaurantInfo(@PathVariable Long restaurantId,
                                                                    Principal principal) {
        Long userId = principal != null ? Long.valueOf(principal.getName()) : null;
        return ResponseEntity.ok(restaurantService.getRestaurantInfo(restaurantId, userId));
    }
}
