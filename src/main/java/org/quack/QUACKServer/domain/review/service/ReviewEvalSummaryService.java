package org.quack.QUACKServer.domain.review.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;
import org.quack.QUACKServer.domain.review.repository.ReviewEvalSummaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewEvalSummaryService {
    private final ReviewEvalSummaryRepository reviewEvalSummaryRepository;

    public List<ReviewEvalSummaryRank> getReviewEvalSummary(Long restaurantId,
                                                            ReviewEnum.ReviewKeywordType keywordType) {

        return reviewEvalSummaryRepository.findByRestaurantIdAndReviewType(restaurantId, keywordType,
                PageRequest.of(0, 3));
    }


}
