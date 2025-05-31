package org.quack.QUACKServer.domain.review.repository;

import org.quack.QUACKServer.domain.review.domain.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

}
