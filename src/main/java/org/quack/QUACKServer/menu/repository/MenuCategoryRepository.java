package org.quack.QUACKServer.menu.repository;

import java.util.List;
import org.quack.QUACKServer.menu.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findMenuCategoryByRestaurantId(Long restaurantId);

}
