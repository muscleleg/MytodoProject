package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;

    public void join(TodoList todoList) {

        TodoList findTodoList = todoListRepository.findByDate(todoList.getRegisterDate(),todoList.getMember());
        if (findTodoList == null) {
            todoListRepository.save(todoList);
        }
    }
//    private void validateDuplicateTodoList(TodoList todoList) {
//        TodoList findTodoList = todoListRepository.findByDate(todoList.getRegisterDate());
//        if (findTodoList!=null) {
//            throw new IllegalStateException("이미 존재하는 TodoList입니다.");
//        }
//    }
}
