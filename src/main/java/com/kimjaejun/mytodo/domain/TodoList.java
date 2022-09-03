package com.kimjaejun.mytodo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoList {
    @Id
    @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "todoList")
    private List<TodolistItem> items = new ArrayList<>();

    private LocalDateTime registerDate;

}
