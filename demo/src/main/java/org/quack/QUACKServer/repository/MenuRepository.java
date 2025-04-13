package org.quack.QUACKServer.repository;

import java.util.List;
import org.quack.QUACKServer.domain.Menu;
import org.quack.QUACKServer.dto.restaurant.MenuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.MenuDto(" +
            "m.menuName, m.menuDescription, m.price, m.menuImage" +
            ") " +
            "FROM Menu m " +
            "WHERE m.restaurant.restaurantId = :restaurantId")
    List<MenuDto> findMenusByRestaurantId(Long restaurantId);
}