package org.quack.QUACKServer.repository;

import java.util.List;
import org.quack.QUACKServer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    int countByUserId(Long userId);

    List<Review> findAllByUserId(Long userId);
}
