package org.quack.QUACKServer.domain.menu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.menu.domain
 * @fileName : MenuEvalSummary
 * @date : 25. 5. 18.
 */
@Entity
@Table(name = "menu_eval_summary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEvalSummary {

    @Id
    @Column(name = "menu_eval_summary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuEvalSummaryId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "history_at", nullable = false)
    private LocalDate historyAt;

    @Column(name = "total_count", nullable = false)
    private Long totalCount;

    @Column(name = "menu_eval_summary_rank")
    private Integer menuEvalSummaryRank;

}
