package org.quack.QUACKServer.dto.reviews.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.ReviewLike;
import org.quack.QUACKServer.domain.common.LikeType;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews.request
 * @fileName : ReviewLikeUpdateRequest
 * @date : 25. 3. 31.
 */
@Builder(access = AccessLevel.PRIVATE)
public record ReviewLikeUpdateResponse(
        Long hackGongGamCount, Long noGongGamCount) {

    public static ReviewLikeUpdateResponse from(List<ReviewLike> reviewLikes) {

        return ReviewLikeUpdateResponse.builder()
                .hackGongGamCount(reviewLikes.stream().filter(reviewLike -> reviewLike.getLikeType().equals(LikeType.HACK_GONG_GAM)).count())
                .noGongGamCount((reviewLikes.stream().filter(reviewLike -> reviewLike.getLikeType().equals(LikeType.NO_GANG_GAM)).count()))
                .build();
    }

}
