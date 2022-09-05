package com.kimjaejun.mytodo.form.todolist;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
public class TodoListAddForm {
    private LocalDate registerDate;
    @NotEmpty
    private String text;
}
