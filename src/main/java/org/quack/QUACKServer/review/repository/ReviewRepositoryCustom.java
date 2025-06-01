package org.quack.QUACKServer.review.repository;

import org.quack.QUACKServer.review.dto.response.ReviewInfoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewInfoResponse> findAllMyReview(Long userId, Pageable pageable);

}
