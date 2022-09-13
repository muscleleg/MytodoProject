package com.kimjaejun.mytodo.domain;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.sql.Time;
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

    protected TimePlanItem() {
    }

    public TimePlanItem(TimePlan timePlan, String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.timePlan = timePlan;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public static TimePlanItem createTimePlanItem(TimePlan timePlan, String title, String content, LocalDateTime startDate, LocalDateTime endDate){
        TimePlanItem timePlanItem = new TimePlanItem(timePlan, title, content, startDate, endDate);
        return timePlanItem;
    }
}
