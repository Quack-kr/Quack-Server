package org.quack.QUACKServer.menu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.menu.enums.MenuEnum;

@Getter
@Entity
@Table(name = "menu_eval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class MenuEval {

    @Id
    @Column(name = "menu_eval_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuEvalId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_eval_type", nullable = false)
    private MenuEnum.MenuEvalType menuEvalType;

    private MenuEval(Long reviewId, Long menuId, MenuEnum.MenuEvalType menuEvalType) {
        this.reviewId = reviewId;
        this.menuId = menuId;
        this.menuEvalType = menuEvalType;
    }

    public static MenuEval create(Long reviewId, Long menuId, MenuEnum.MenuEvalType menuEvalType) {
        return new MenuEval(reviewId, menuId, menuEvalType);
    }

}
