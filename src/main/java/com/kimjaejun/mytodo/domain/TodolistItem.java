package com.kimjaejun.mytodo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TodolistItem {
    @Id
    @GeneratedValue
    @Column(name = "todolistitem_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todolist_id")
    private TodoList todoList;

    private LocalDateTime registerDate;

    private SuccessStatus status;

    private int statusPercentage;
}
