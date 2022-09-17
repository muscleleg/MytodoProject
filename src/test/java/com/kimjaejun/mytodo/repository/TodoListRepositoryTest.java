//package com.kimjaejun.mytodo.repository;
//
//import com.kimjaejun.mytodo.domain.Member;
//import com.kimjaejun.mytodo.domain.TodoList;
//import com.kimjaejun.mytodo.service.MemberService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class TodoListRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    TodoListRepository todoListRepository;
//
//    @Test
//    @Transactional
//    void save() {
//        Member member = memberRepository.findByLoginId("test");
//        TodoList todoList = TodoList.createTodoList(member, LocalDate.now());
//        todoListRepository.save(todoList);
//        TodoList findTodoList = todoListRepository.findByMemberId(member);
//
//    }
//}