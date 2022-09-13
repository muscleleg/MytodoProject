package com.kimjaejun.mytodo.domain;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
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

    private LocalTime startDate;
    private LocalTime endDate;

    protected TimePlanItem() {
    }

    public TimePlanItem(TimePlan timePlan, String title,  LocalTime startDate, LocalTime endDate) {
        this.timePlan = timePlan;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public static TimePlanItem createTimePlanItem(TimePlan timePlan, String title,  LocalTime startDate, LocalTime endDate){
        TimePlanItem timePlanItem = new TimePlanItem(timePlan, title, startDate, endDate);
        return timePlanItem;
    }
}
