package com.kimjaejun.mytodo.controller.todolist;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.form.todolist.TodoListAddForm;
import com.kimjaejun.mytodo.repository.MemberRepository;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import com.kimjaejun.mytodo.service.MemberService;
import com.kimjaejun.mytodo.service.TodoListItemService;
import com.kimjaejun.mytodo.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodolistController {
    private final TodoListService todoListService;
    private final TodoListRepository todoListRepository;
    private final TodoListItemService todoListItemService;
    private final TodoListItemRepository todoListItemRepository;

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @GetMapping("/todolist")
    public String todolist(Model model) {
        TodoListAddForm todoListAddForm = new TodoListAddForm();
        todoListAddForm.setRegisterDate(LocalDate.now());
        List<TodoListItem> findItems = todoListItemRepository.findByDate(LocalDate.now());
        model.addAttribute("todoList",findItems);
        model.addAttribute("addform", todoListAddForm);

        return "todolist";
    }

    @PostMapping("/todolist/{date}/add")
    public String addTodoListItem(@PathVariable("date") String addDate, @ModelAttribute("form") TodoListAddForm form,HttpSession session) {
        LocalDate date = LocalDate.parse(addDate, DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberRepository.findOne(member.getId());
        String text = form.getText();
        todoListItemService.join(date,member,text);
//        TodoList todoList = todoListRepository.findByDate(date);
//        if (todoList == null) {
//            todoList = TodoList.createTodoList(member, date);
////            todoListService.join(todoList);
//        }
//        TodoListItem todoListItem = new TodoListItem(form.getText(), todoList, date, 0);
//        todoListItemRepository.save(todoListItem);
        return "redirect:/todolist";
    }

}
