package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class TodoListItemRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    TodoListItemRepository todoListItemRepository;
    @Autowired
    TodoListRepository todoListRepository;

    @Test
    @DisplayName("날짜로 TodoListItems 찾기")
    void findByDate() {
        Member member = Member.createMember("a", "a", "aa", "male", "aamoof@gmail.com");
        em.persist(member);
        TodoList todoList = TodoList.createTodoList(member, LocalDate.now());
        em.persist(todoList);
//        TodoListItem todoListItem1 = TodoListItem.creatTodoListItem("hello", todoList, LocalDate.now(),0);
//        TodoListItem todoListItem2 = TodoListItem.creatTodoListItem("hello", todoList, LocalDate.now(),0);
        List<TodoListItem> listItems = new ArrayList<>();
        listItems.add(todoListItem1);
        listItems.add(todoListItem2);

        todoListItemRepository.save(todoListItem1);
        todoListItemRepository.save(todoListItem2);
        List<TodoListItem> findList = todoListItemRepository.findByDate(LocalDate.now(),todoList);
        Assertions.assertThat(findList).isEqualTo(listItems);
    }
}