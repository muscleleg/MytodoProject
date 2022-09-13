package com.kimjaejun.mytodo.form.timeplan;

import com.kimjaejun.mytodo.domain.TimePlan;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class TimePlanItemAddForm {
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;
}
