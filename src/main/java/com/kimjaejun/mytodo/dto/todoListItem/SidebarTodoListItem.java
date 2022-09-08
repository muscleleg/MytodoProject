package com.kimjaejun.mytodo.dto.todoListItem;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SidebarTodoListItem {
    private LocalDate date;
    private String text;

    private SidebarTodoListItem() {

    }
    public SidebarTodoListItem(LocalDate date, String text) {
        this.date = date;
        this.text = text;
    }

    public static SidebarTodoListItem createSidebarTodoListItem(LocalDate date, String text) {
        SidebarTodoListItem sidebarTodoListItem = new SidebarTodoListItem(date, text);
        return sidebarTodoListItem;
    }
}
