package org.quack.QUACKServer.demo.service;


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.domain.common.KeywordType;
import org.quack.QUACKServer.demo.dto.restaurant.ReviewKeywordDto;
import org.quack.QUACKServer.demo.repository.ReviewKeywordRepository;
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
