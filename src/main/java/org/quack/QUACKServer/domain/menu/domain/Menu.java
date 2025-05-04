package org.quack.QUACKServer.domain.menu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "menu_price", nullable = false)
    private Long menuPrice;

    @Column(name = "menu_description", nullable = false)
    private String menuDescription;

    // TODO : 웹단에서 처리를 해줘야함
    // private Long menuCategoryId;

    // TODO: 이미지 후처리
    // private String menuImagePath;

}
