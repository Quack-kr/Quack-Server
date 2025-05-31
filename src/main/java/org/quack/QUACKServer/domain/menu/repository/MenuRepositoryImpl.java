package org.quack.QUACKServer.domain.menu.repository;

import static org.quack.QUACKServer.domain.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.domain.menu.domain.QMenuCategory.menuCategory;
import static org.quack.QUACKServer.domain.photos.domain.QPhotos.photos;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<GetReviewMenusResponse> getReviewMenus(Long restaurantId) {
        List<Tuple> reviewMenus = queryFactory
                .select(menu,
                        menuCategory.categoryName,
                        photos.imageUrl)
                .from(menu)
                .leftJoin(menuCategory)
                .on(menuCategory.menuCategoryId.eq(menu.menuCategoryId)
                        .and(menuCategory.restaurantId.eq(menu.restaurantId)))
                .leftJoin(photos)
                .on(photos.targetId.eq(menu.menuId)
                        .and(photos.photoType.eq("menu_board")))
                .where(menu.restaurantId.eq(restaurantId))
                .fetch();

        List<GetReviewMenusResponse> results = new ArrayList<>();

        for (Tuple reviewMenu : reviewMenus) {

            GetReviewMenusResponse response = GetReviewMenusResponse.of(
                    reviewMenu.get(menu.menuName),
                    reviewMenu.get(menuCategory.categoryName),
                    reviewMenu.get(menu.menuPrice),
                    reviewMenu.get(photos.imageUrl)
            );

            results.add(response);
        }

        return results;
    }
}
