package org.quack.QUACKServer.demo.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.dto.restaurant.MenuDto;
import org.quack.QUACKServer.demo.repository.MenuRepository;
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
