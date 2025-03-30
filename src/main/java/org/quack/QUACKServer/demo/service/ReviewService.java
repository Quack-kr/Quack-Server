package org.quack.QUACKServer.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.domain.Review;
import org.quack.QUACKServer.demo.domain.ReviewPhoto;
import org.quack.QUACKServer.demo.dto.restaurant.RecentReviewDto;
import org.quack.QUACKServer.demo.dto.restaurant.ReviewMenuDto;
import org.quack.QUACKServer.demo.repository.ReviewLikeRepository;
import org.quack.QUACKServer.demo.repository.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public int getReviewCountByUserId(Long userId) {
        return reviewRepository.countByUser_UserId(userId);
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
        return reviewRepository.findAllByUser_UserId(userId);
    }

    public List<RecentReviewDto> getRecentReviewByRestaurantId(Long restaurantId) {
//        List<Review> reviews = reviewRepository.findRecentReviewsByRestaurantId(restaurantId, 50,
//                PageRequest.of(0, 3));
//
//        List<RecentReviewDto> recentReviews = reviews.stream()
//                .map(r -> new RecentReviewDto(
//                        r.getReviewId(),
//                        r.getUser().getNickname(),
//                        r.getUser().getProfileImage(),
//                        r.getCreatedDate(),
//                        getReviewPhoto(r),
//                        getReviewMenuDto(r)
//                ))
//                .toList();
        return null;
    }

    private List<String> getReviewPhoto(Review review) {
        return review.getReviewPhotos()
                .stream()
                .map(ReviewPhoto::getPhotoUrl)
                .collect(Collectors.toList());
    }

    private List<ReviewMenuDto> getReviewMenuDto(Review review){
//        return review.getReviewMenus().stream()
//                .map(rm -> new ReviewMenuDto(rm.getMenu().getMenuName(), rm.getEvaluation()))
//                .collect(Collectors.toList());
        return null;
    }
}
