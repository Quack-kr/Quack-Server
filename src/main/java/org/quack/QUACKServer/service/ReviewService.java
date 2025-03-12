package org.quack.QUACKServer.service;

import static org.quack.QUACKServer.domain.common.LikeType.노공감;
import static org.quack.QUACKServer.domain.common.LikeType.핵공감;

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

    public double getEmpathyDecibelByUserId(Long userId) {
        Double empathyDecibel = reviewLikeRepository.aggregateEmpathyDecibelByUserId(userId);
        if (empathyDecibel == null || empathyDecibel < 0.0) {
            empathyDecibel = 0.0;
        }
        return empathyDecibel;
    }

    public List<Review> getReviewsByUserId(Long userId) {
        // iOS에서 요청이 어떻게 넘어오는지 확인하고 paging 해야함
        return reviewRepository.findAllByUserId(userId);
    }
}
