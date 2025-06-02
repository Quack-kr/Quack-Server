package org.quack.QUACKServer.menu.repository;

import java.util.List;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;

public interface MenuRepositoryCustom {
    List<GetReviewMenusResponse> getReviewMenus(Long restaurantId);
}
