package com.kimjaejun.mytodo.form.timeplan;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
public class TimePlanAddForm {
    private Long Id;
    private String title;
    private LocalDate registerDate;

    public TimePlanAddForm() {
    }

    public TimePlanAddForm(Long id, String title, LocalDate registerDate) {
        Id = id;
        this.title = title;
        this.registerDate = registerDate;
    }
}
