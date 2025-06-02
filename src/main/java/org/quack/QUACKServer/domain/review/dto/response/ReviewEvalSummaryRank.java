package org.quack.QUACKServer.domain.review.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewEvalSummaryRank(
        Integer ranking,
        ReviewEnum.ReviewKeywordType reviewKeywordType,
        ReviewEnum.ReviewTag reviewTag,
        Long totalCount
) {
    public static ReviewEvalSummaryRank of(Integer ranking, ReviewEnum.ReviewKeywordType reviewKeywordType,
                                           ReviewEnum.ReviewTag reviewTag, Long totalCount) {
        return ReviewEvalSummaryRank.builder()
                .ranking(ranking)
                .reviewKeywordType(reviewKeywordType)
                .reviewTag(reviewTag)
                .totalCount(totalCount)
                .build();
    }
}
