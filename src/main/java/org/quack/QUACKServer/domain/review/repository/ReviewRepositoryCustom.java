package org.quack.QUACKServer.domain.review.repository;

import org.quack.QUACKServer.domain.review.dto.response.ReviewInfoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewInfoResponse> findAllMyReview(Long userId, Pageable pageable);

}
