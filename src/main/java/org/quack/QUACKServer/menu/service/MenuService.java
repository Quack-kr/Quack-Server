package org.quack.QUACKServer.menu.service;


import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.menu.domain.MenuCategory;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.menu.repository.MenuCategoryRepository;
import org.quack.QUACKServer.menu.repository.MenuRepository;
import org.quack.QUACKServer.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.photos.repository.PhotosS3Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            List<GetReviewMenusResponse> menuList = results.get(reviewMenu.menuCategory());
            menuList.add(reviewMenu);

            results.put(reviewMenu.menuCategory(), menuList);
        }

        return results;
    }

    public List<MenuSimpleInfo> getMenuSimpleInfo(Long restaurantId) {
        return menuRepository.findMenuSimpleInfoByRestaurantId(restaurantId);
    }
}
