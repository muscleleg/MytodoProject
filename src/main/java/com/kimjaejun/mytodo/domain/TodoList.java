package com.kimjaejun.mytodo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TodoList {
    @Id
    @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;

//    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    private LocalDate registerDate;
    @OneToMany(mappedBy = "todoList")
    private List<TodoListItem> items = new ArrayList<>();
    protected TodoList() {
    }

    public TodoList(Member member, LocalDate registerDate) {
        this.member = member;
        this.registerDate = registerDate;
    }

    public static TodoList createTodoList(Member member, LocalDate registerDate) {
        TodoList todoList = new TodoList(member,registerDate);
        return todoList;
    }
}
