package com.kimjaejun.mytodo.controller.testData;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.repository.MemberRepository;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import com.kimjaejun.mytodo.service.MemberService;
import com.kimjaejun.mytodo.service.TodoListItemService;
import com.kimjaejun.mytodo.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class initData {
    private final TodoListService todoListService;
    private final TodoListRepository todoListRepository;
    private final TodoListItemService todoListItemService;
    private final TodoListItemRepository todoListItemRepository;

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @PostConstruct
    public void initMember() {
        Member member = Member.createMember("test","test","testUser","none","test@gmail.com");
        memberService.join(member);
        Member findmember = memberRepository.findByLoginId("test");
        for (int i = 1; i <= 12; i++) {
            LocalDate date = LocalDate.of(2022, i, 1);
            TodoList todoList = TodoList.createTodoList(member,date);
            todoListService.join(todoList);
            TodoListItem todoListItem = TodoListItem.creatTodoListItem("test", todoList, member, date, i);
            todoListItemService.join(todoListItem);
        }

    }

    @PostConstruct
    public void initToDoList() {

    }
}
