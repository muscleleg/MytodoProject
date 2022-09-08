package com.kimjaejun.mytodo.domain;

import lombok.Data;
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

    @Column(length = 1000)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todolist_id")
    private TodoList todoList;




    private LocalDate registerDate;

//    private SuccessStatus status;

    private int statusPercentage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    protected TodoListItem() {
    }

    public TodoListItem(String title, TodoList todoList,Member member, LocalDate registerDate, int statusPercentage) {
        this.title = title;
        this.todoList = todoList;
        this.member = member;
        this.registerDate = registerDate;
        this.statusPercentage = statusPercentage;
    }
    public void changePercentage(int statusPercentage) {
        this.statusPercentage = statusPercentage;
    }
    public static TodoListItem creatTodoListItem(String title, TodoList todoList,Member member, LocalDate registerDate, int statusPercentage) {
        TodoListItem todoListItem = new TodoListItem(title, todoList,member, registerDate,statusPercentage);
        return todoListItem;
    }
}
