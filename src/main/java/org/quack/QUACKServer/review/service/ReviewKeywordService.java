package org.quack.QUACKServer.review.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.review.repository.ReviewKeywordRepository;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewKeywordService {
    private final ReviewKeywordRepository reviewKeywordRepository;

    public void saveReviewKeyword(Long reviewId, List<Long> reviewKeywords, ReviewEnum.ReviewKeywordType reviewKeywordType) {

    }
}
