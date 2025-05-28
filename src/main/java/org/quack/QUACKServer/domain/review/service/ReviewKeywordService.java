package org.quack.QUACKServer.domain.review.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewKeywordType;
import org.quack.QUACKServer.domain.review.repository.ReviewKeywordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewKeywordService {
    private final ReviewKeywordRepository reviewKeywordRepository;

    public void saveReviewKeyword(Long reviewId, List<Long> reviewKeywords, ReviewKeywordType reviewKeywordType) {

    }
}
