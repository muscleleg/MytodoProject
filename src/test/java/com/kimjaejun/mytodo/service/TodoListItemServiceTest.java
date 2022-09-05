package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.repository.MemberRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TodoListItemServiceTest {
    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void findtest() {
        Member member = Member.createMember("hello","hello","hello","male","aamoof@naver.com");
        memberRepository.save(member);
        LocalDate date = LocalDate.now();
        TodoList todoList = TodoList.createTodoList(member,date);
        todoListRepository.save(todoList);
        TodoList findTodoList = todoListRepository.findByDate(date);
        Assertions.assertThat(todoList).isEqualTo(findTodoList);
    }
}