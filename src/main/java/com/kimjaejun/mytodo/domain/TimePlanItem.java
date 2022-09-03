package com.kimjaejun.mytodo.domain;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TimePlanItem {
    @Id
    @GeneratedValue
    @Column(name = "timepalnitem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeplan_id")
    private TimePlan timePlan;

    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
