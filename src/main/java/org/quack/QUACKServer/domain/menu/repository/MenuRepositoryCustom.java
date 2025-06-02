package org.quack.QUACKServer.domain.menu.repository;

import java.util.List;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.domain.menu.dto.response.MenuSimpleInfo;

public interface MenuRepositoryCustom {
    List<GetReviewMenusResponse> getReviewMenus(Long restaurantId);
    List<MenuSimpleInfo> findMenuSimpleInfoByRestaurantId(Long restaurantId);
}
