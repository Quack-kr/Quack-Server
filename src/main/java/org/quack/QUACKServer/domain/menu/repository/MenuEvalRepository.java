package org.quack.QUACKServer.domain.menu.repository;

import org.quack.QUACKServer.domain.menu.domain.MenuEval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuEvalRepository extends JpaRepository<MenuEval, Long>, MenuEvalRepositoryCustom {
    boolean deleteMenuEvalByReviewId(Long reviewId);
}
