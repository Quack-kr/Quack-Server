package org.quack.QUACKServer.domain.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewSimpleInfo(
        String writerProfileImage,
        String writerNickname,
        LocalDateTime reviewCreatedAt,
        String reviewRepresentativeImage,
        String reviewContent,
        List<MenuEvalResponse> menuEvalList
) {
    public static ReviewSimpleInfo of(String writerProfileImage, String writerNickname, LocalDateTime reviewCreatedAt,
                                      String reviewRepresentativeImage, String reviewContent,
                                      List<MenuEvalResponse> menuEvalList) {
        return ReviewSimpleInfo.builder()
                .writerProfileImage(writerProfileImage)
                .writerNickname(writerNickname)
                .reviewCreatedAt(reviewCreatedAt)
                .reviewRepresentativeImage(reviewRepresentativeImage)
                .reviewContent(reviewContent)
                .menuEvalList(menuEvalList)
                .build();
    }
}
