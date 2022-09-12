package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.form.todolist.TodoListAddForm;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoListItemService {
    private final TodoListService todoListService;
    private final TodoListRepository todoListRepository;
    private final TodoListItemRepository todoListItemRepository;
    //테스트용
    public void join(TodoListItem todoListItem){
        todoListItemRepository.save(todoListItem);
    }
    //toDolistItem 웹에서 생성할때용
    public void join(LocalDate date, Member member, String text) {

        TodoList todoList = todoListRepository.findByDate(date, member);
        if (todoList == null) {
            todoList = TodoList.createTodoList(member, date);
            todoListService.join(todoList);
        }
        TodoListItem todoListItem = new TodoListItem(text, todoList, member, date, 0);
        todoListItemRepository.save(todoListItem);
    }
    public void updatePercentage(Long todoListItemId, int statusPercentage){
        TodoListItem todoListItem = todoListItemRepository.findOne(todoListItemId);
        todoListItem.changePercentage(statusPercentage);
    }
    public void delete(Long todoListItemId){
        TodoListItem todoListItem = todoListItemRepository.findOne(todoListItemId);
        todoListItemRepository.remove(todoListItem);
    }
//    @PostMapping("/todolist/{date}/add")
//    public String addTodoListItem(@PathVariable("date") String addDate, @ModelAttribute("form") TodoListAddForm form, HttpSession session) {
//        LocalDate date = LocalDate.parse(addDate, DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
//        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        TodoList todoList = todoListRepository.findByDate(date);
//        if (todoList == null) {
//            todoList = TodoList.createTodoList(member, date);
////            todoListService.join(todoList);
//        }
//        TodoListItem todoListItem = new TodoListItem(form.getText(), todoList, date, 0);
//        todoListItemRepository.save(todoListItem);
//        return "redirect:/todolist";
//    }
}
