package org.quack.QUACKServer.service;


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.common.KeywordType;
import org.quack.QUACKServer.dto.restaurant.ReviewKeywordDto;
import org.quack.QUACKServer.repository.ReviewKeywordRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewKeywordService {
    private final ReviewKeywordRepository reviewKeywordRepository;

    public List<ReviewKeywordDto> getReviewKeyword(Long restaurantId, KeywordType keywordType, Pageable pageable) {
        List<ReviewKeywordDto> evaluations = reviewKeywordRepository.findTopKeywordsByType(restaurantId,
                LocalDateTime.now().minusMonths(3), keywordType, pageable);
        return evaluations;
    }
}
