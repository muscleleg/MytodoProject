package com.kimjaejun.mytodo.form.timeplan;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter@Setter
public class TimePlanAddForm {
    @NotEmpty(message = "제목 입력은 필수 입니다.")
    private String title;
    private LocalDate registerDate;

    public TimePlanAddForm() {
    }

    public TimePlanAddForm(String title, LocalDate registerDate) {
        this.title = title;
        this.registerDate = registerDate;
    }
}
