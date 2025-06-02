package org.quack.QUACKServer.domain.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.domain.MenuCategory;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.domain.menu.repository.MenuCategoryRepository;
import org.quack.QUACKServer.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public Map<String, List<GetReviewMenusResponse>> getMenusForReview(Long restaurantId) {
        List<GetReviewMenusResponse> reviewMenus = menuRepository.getReviewMenus(restaurantId);

        List<MenuCategory> menuCategory = menuCategoryRepository.findMenuCategoryByRestaurantId((restaurantId));

        Map<String, List<GetReviewMenusResponse>> results = new HashMap<>();

        for (MenuCategory category : menuCategory) {
            results.put(category.getCategoryName(), new ArrayList<>());
        }


        for (GetReviewMenusResponse reviewMenu : reviewMenus) {
            List<GetReviewMenusResponse> menuList = results.get(reviewMenu.getMenuCategory());
            menuList.add(reviewMenu);

            results.put(reviewMenu.getMenuCategory(), menuList);
        }

        return results;
    }

    public List<MenuSimpleInfo> getMenuSimpleInfo(Long restaurantId) {
        return menuRepository.findMenuSimpleInfoByRestaurantId(restaurantId);
    }
}
