package org.quack.QUACKServer.menu.repository;

import org.quack.QUACKServer.menu.domain.MenuEval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuEvalRepository extends JpaRepository<MenuEval, Long>, MenuEvalRepositoryCustom {
    boolean deleteMenuEvalByReviewId(Long reviewId);
}
