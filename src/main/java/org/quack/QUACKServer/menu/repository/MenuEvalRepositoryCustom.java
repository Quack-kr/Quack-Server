package org.quack.QUACKServer.menu.repository;

import java.util.List;
import org.quack.QUACKServer.menu.dto.response.BestMenu;
import org.quack.QUACKServer.menu.dto.response.MenuEvalResponse;

public interface MenuEvalRepositoryCustom {
    List<MenuEvalResponse> getMenuEvals(Long reviewId);
    List<BestMenu> findBestMenu(Long restaurantId);
}
