package org.quack.QUACKServer.domain.menu.repository;

import java.util.List;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;

public interface MenuEvalRepositoryCustom {
    List<MenuEvalResponse> getMenuEvals(Long reviewId);
}
