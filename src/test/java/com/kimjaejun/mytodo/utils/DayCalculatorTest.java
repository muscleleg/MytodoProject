package com.kimjaejun.mytodo.utils;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.repository.MemberRepository;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import com.kimjaejun.mytodo.service.TodoListItemService;
import com.kimjaejun.mytodo.service.TodoListService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class DayCalculatorTest {
    @Autowired
    TodoListItemRepository todoListItemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TodoListService todoListService;
    @Autowired
    TodoListItemService todoListItemService;
    @Test
    public void 요번달_테스트() {
        //given
        Member member = memberRepository.findByLogin("test", "test");
        LocalDate date = LocalDate.now();
        TodoList todoList = todoListRepository.findByDate(date, member);

        /*
        PostConstructor로 2022-09-11, statusPercentage:0 미리 하나 들어가 있음 테스트의 데이터 개수는 총 3개

         */
        TodoListItem todoListItem1 = TodoListItem.creatTodoListItem("test1", todoList, member, date, 10);
        todoListItemRepository.save(todoListItem1);
        TodoListItem todoListItem2 = TodoListItem.creatTodoListItem("test2", todoList, member, date, 20);
        todoListItemRepository.save(todoListItem2);

        //when
        Double avgTest = todoListItemRepository.findBySuccessPercentageThisMonth(member);

        //then
        Assertions.assertThat(avgTest).isEqualTo(10.0);


    }

}