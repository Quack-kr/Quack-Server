package org.quack.QUACKServer.domain.menu.repository;

import static org.quack.QUACKServer.domain.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.domain.menu.domain.QMenuEval.menuEval;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
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

}
