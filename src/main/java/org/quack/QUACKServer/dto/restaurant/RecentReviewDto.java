package org.quack.QUACKServer.dto.restaurant;

import java.time.LocalDateTime;
import java.util.List;

public record RecentReviewDto(
        Long reviewId,
        String userNickname,
        String userProfileImage,
        LocalDateTime createdDate,
        List<String> reviewPhotos,
        List<String> keywords,
        List<ReviewMenuDto> menus
) { }
