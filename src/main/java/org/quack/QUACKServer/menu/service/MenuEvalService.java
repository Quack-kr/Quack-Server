package org.quack.QUACKServer.menu.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.menu.domain.MenuEval;
import org.quack.QUACKServer.menu.dto.response.BestMenu;
import org.quack.QUACKServer.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.menu.repository.MenuEvalRepository;
import org.quack.QUACKServer.review.dto.request.MenuEvalRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuEvalService {

    private final MenuEvalRepository menuEvalRepository;

    public void saveMenusEval(Long reviewId, List<MenuEvalRequest> menusEval){

        List<MenuEval> menuEvalList = menusEval.stream()
                .map(req -> MenuEval.create(reviewId, req.menuId(), req.evalType()))
                .collect(Collectors.toList());

        menuEvalRepository.saveAll(menuEvalList);
    }

    public List<MenuEvalResponse> getMenuEvalsForReview(Long reviewId) {
        return menuEvalRepository.getMenuEvals(reviewId);
    }

    @Transactional
    public boolean menuEvalDelete(Long reviewId) {
        return menuEvalRepository.deleteMenuEvalByReviewId(reviewId);
    }

    public List<BestMenu> getRestaurantBestMenu(Long restaurantId) {
        return menuEvalRepository.findBestMenu(restaurantId);
    }
}
