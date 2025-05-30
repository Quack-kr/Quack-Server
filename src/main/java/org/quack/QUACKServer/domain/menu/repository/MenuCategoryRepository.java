package org.quack.QUACKServer.domain.menu.repository;

import java.util.List;
import org.quack.QUACKServer.domain.menu.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findMenuCategoryByRestaurantId(Long restaurantId);

}
