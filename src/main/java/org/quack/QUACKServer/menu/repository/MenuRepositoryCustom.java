package org.quack.QUACKServer.menu.repository;

import java.util.List;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;

public interface MenuRepositoryCustom {
    List<GetReviewMenusResponse> getReviewMenus(Long restaurantId);
    List<MenuSimpleInfo> findMenuSimpleInfoByRestaurantId(Long restaurantId);
}
