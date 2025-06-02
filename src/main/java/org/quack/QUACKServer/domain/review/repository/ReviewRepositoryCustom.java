package org.quack.QUACKServer.domain.review.repository;

import java.util.List;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInfoResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewSimpleInfo;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    List<ReviewInfoResponse> findAllMyReview(Long userId, Pageable pageable);
    List<ReviewSimpleInfo> findByRestaurantRecentReview(Long restaurantId);

}
