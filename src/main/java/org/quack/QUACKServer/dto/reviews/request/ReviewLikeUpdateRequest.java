package org.quack.QUACKServer.dto.reviews.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews.request
 * @fileName : ReviewLikeUpdateRequest
 * @date : 25. 3. 31.
 */
public record ReviewLikeUpdateRequest(
        @NotNull(message = "restaurant_id 가 비어있으면 안됩니다.") Long restaurantId,
        @NotNull(message = "restaurant_id 가 비어있으면 안됩니다.") Long reviewId,
        @NotBlank String myGongGam
) {
}
