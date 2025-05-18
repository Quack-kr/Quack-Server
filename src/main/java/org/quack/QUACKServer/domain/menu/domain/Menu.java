package org.quack.QUACKServer.domain.menu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "category_id")
    private Long menuCategoryId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private Long menuPrice;

    @Column(name = "description", nullable = false)
    private String menuDescription;

    @Column(name = "sort_order")
    private Integer menuSortOrder;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
