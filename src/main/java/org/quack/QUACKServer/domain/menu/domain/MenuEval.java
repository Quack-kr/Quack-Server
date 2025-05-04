package org.quack.QUACKServer.domain.menu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.menu.enums.MenuEnum;

@Getter
@Entity
@Table(name = "menu_eval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class MenuEval {

    @Id
    @GeneratedValue
    private Long evalId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_eval_type", nullable = false)
    private MenuEnum.MenuEvalType menuEvalType;

}
