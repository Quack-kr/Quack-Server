package org.quack.QUACKServer.repository.review;

import org.quack.QUACKServer.domain.ReviewMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.repository
 * @fileName : ReviewMenuRepository
 * @date : 25. 3. 31.
 */

@Repository
public interface ReviewMenuRepository extends JpaRepository<ReviewMenu, Long> {

}
