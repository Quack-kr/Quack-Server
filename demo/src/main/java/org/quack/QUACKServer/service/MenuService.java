package org.quack.QUACKServer.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.restaurant.MenuDto;
import org.quack.QUACKServer.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuDto> getMenus(Long restaurantId) {
        List<MenuDto> menus = menuRepository.findMenusByRestaurantId(restaurantId);
        return menus;
    }
}
