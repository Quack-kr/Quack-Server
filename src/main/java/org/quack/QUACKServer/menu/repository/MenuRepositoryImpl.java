package org.quack.QUACKServer.menu.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.quack.QUACKServer.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.menu.domain.QMenuCategory.menuCategory;
import static org.quack.QUACKServer.photos.domain.QPhotos.photos;

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

    public List<MenuSimpleInfo> findMenuSimpleInfoByRestaurantId(Long restaurantId){
        return queryFactory
                .select(Projections.constructor(
                        MenuSimpleInfo.class,
                        photos.imageUrl,
                        menu.menuName,
                        menu.menuPrice
                ))
                .from(menu)
                .leftJoin(photos).on(photos.targetId.eq(menu.menuId)
                        .and(photos.photoType.eq("menu_board")))
                .where(menu.restaurantId.eq(restaurantId))
                .fetch();
    }
}
