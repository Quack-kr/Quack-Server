package org.quack.QUACKServer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.Review;
import org.quack.QUACKServer.repository.ReviewLikeRepository;
import org.quack.QUACKServer.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public int getReviewCountByUserId(Long userId) {
        return reviewRepository.countByUserId(userId);
    }

    public int getEmpathyDecibelByUserId(Long userId) {
        // userId로 사용자가 작성한 Review를 모두 불러오기
        // 해당 리뷰마다 핵공감, 노공감 횟수를 보고 숫자로 계산
        return 0;
    }

    public List<Review> getReviewsByUserId(Long userId) {
        // iOS에서 요청이 어떻게 넘어오는지 확인하고 paging 해야함
        return reviewRepository.findAllByUserId(userId);
    }
}
