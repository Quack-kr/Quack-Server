package org.quack.QUACKServer.domain.menu.repository;

import static org.quack.QUACKServer.domain.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.domain.menu.domain.QMenuEval.menuEval;
import static org.quack.QUACKServer.domain.menu.domain.QMenuEvalSummary.menuEvalSummary;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.BestMenu;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.domain.menu.enums.MenuEnum;
import org.quack.QUACKServer.domain.menu.enums.MenuEnum.MenuEvalType;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class MenuEvalRepositoryImpl implements MenuEvalRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public List<MenuEvalResponse> getMenuEvals(Long reviewId){
        List<Tuple> menuEvals = queryFactory
                .select(menuEval,
                        menu.menuName)
                .from(menuEval)
                .leftJoin(menu)
                .on(menu.menuId.eq(menuEval.menuId))
                .where(menuEval.reviewId.eq(reviewId))
                .fetch();

        List<MenuEvalResponse> results = new ArrayList<>();

        for (Tuple tuple : menuEvals) {
            MenuEvalResponse response = MenuEvalResponse.of(tuple.get(menu.menuName), tuple.get(menuEval.menuEvalType));
            results.add(response);
        }

        return results;
    }

    public List<BestMenu> findBestMenu(Long restaurantId) {

        List<Long> menuIds = queryFactory
                .select(menu.menuId)
                .from(menu)
                .where(menu.restaurantId.eq(restaurantId))
                .fetch();

        if (menuIds.isEmpty()) return List.of();

        List<Tuple> topMenus = queryFactory
                .select(menu.menuId, menuEvalSummary.menuEvalSummaryRank, menu.menuName)
                .from(menu)
                .join(menuEvalSummary).on(menu.menuId.eq(menuEvalSummary.menuId))
                .where(
                        menu.menuId.in(menuIds),
                        menuEvalSummary.menuEvalSummaryRank.loe(3)
                )
                .orderBy(menuEvalSummary.menuEvalSummaryRank.asc())
                .fetch();

        List<Long> topMenuIds = topMenus.stream()
                .map(t -> t.get(menu.menuId))
                .toList();


        List<Tuple> evalCounts = queryFactory
                .select(
                        menuEval.menuId,
                        menuEval.menuEvalType,
                        menuEval.count()
                )
                .from(menuEval)
                .where(menuEval.menuId.in(topMenuIds))
                .groupBy(menuEval.menuId, menuEval.menuEvalType)
                .fetch();

        Map<Long, Map<MenuEvalType, Long>> evalMap = evalCounts.stream()
                .collect(Collectors.groupingBy(
                        t -> t.get(menuEval.menuId),
                        Collectors.toMap(
                                t -> t.get(menuEval.menuEvalType),
                                t -> t.get(2, Long.class)
                        )
                ));

        return topMenus.stream()
                .map(t -> {
                    Long menuId = t.get(menu.menuId);
                    return BestMenu.of(
                            t.get(menuEvalSummary.menuEvalSummaryRank),
                            t.get(menu.menuName),
                            evalMap.getOrDefault(menuId, Map.of())
                    );
                })
                .toList();
    }

}
