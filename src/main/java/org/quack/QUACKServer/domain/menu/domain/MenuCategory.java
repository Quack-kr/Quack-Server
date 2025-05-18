package org.quack.QUACKServer.domain.menu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.menu.domain
 * @fileName : MenuCategory
 * @date : 25. 5. 18.
 */

@Entity
@Table(name = "menu_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategory {

    @Id
    @Column(name = "menu_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuCategoryId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "updated_at")
    private LocalDateTime createAt;
}
