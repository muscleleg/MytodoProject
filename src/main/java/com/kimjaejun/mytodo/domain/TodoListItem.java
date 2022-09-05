package com.kimjaejun.mytodo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class TodoListItem {
    @Id
    @GeneratedValue
    @Column(name = "todolistitem_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todolist_id")
    private TodoList todoList;

    private LocalDate registerDate;

//    private SuccessStatus status;

    private int statusPercentage;

    protected TodoListItem() {
    }

    public TodoListItem(String title, TodoList todoList, LocalDate registerDate, int statusPercentage) {
        this.title = title;
        this.todoList = todoList;
        this.registerDate = registerDate;
        this.statusPercentage = statusPercentage;
    }

    public static TodoListItem creatTodoListItem(String title, TodoList todoList, LocalDate registerDate, int statusPercentage) {
        TodoListItem todoListItem = new TodoListItem(title, todoList, registerDate,statusPercentage);
        return todoListItem;
    }
}
