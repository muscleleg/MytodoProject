package com.kimjaejun.mytodo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TimeTableDTO {
    private String title;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private int today;
    private int nextDay;

    public TimeTableDTO(String title, int startHour, int startMin, int endHour, int endMin, int today, int nextDay) {
        this.title = title;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.today = today;
        this.nextDay = nextDay;
    }
}
