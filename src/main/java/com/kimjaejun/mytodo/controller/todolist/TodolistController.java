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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
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
    public String todolist() {
        LocalDate date = LocalDate.now();
        String stringDate = date.format(DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
        return "redirect:/todolist/" + stringDate;
//        return "todolist";
    }
    @PostMapping("/todolist/search")
    public String searchTodoList(@RequestParam("search")String search,HttpSession session,Model model) {
        //==어디서든 날짜쓰려고 편의상 넣은 것==//
        LocalDate date = LocalDate.now();
        String text = search;
        model.addAttribute("searchValue", text);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (text.equals("") || text == null) {
            List<TodoListItem> findItems = todoListItemRepository.findByMember(member);
            model.addAttribute("todoList",findItems);

        }else{
            List<TodoListItem> findItems = todoListItemRepository.findByText(member,text);
            model.addAttribute("todoList",findItems);

        }
        List<TodoListItem> noSuccess = todoListItemRepository.findByNoSuccess(member);
        model.addAttribute("noSuccessTodoList",noSuccess);
        return "searchTodolist";
    }
    @GetMapping("/todolist/{date}")
    public String addTodoListItem(@PathVariable("date") String stringDate,HttpSession session, Model model,HttpServletRequest request) {
        String error = request.getParameter("error");
        if(error!=null&&error.equals("titleNull")){
            model.addAttribute("error", "titleNull");
        }
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));

        //==빈폼 보내기, todolist.html의 addform에 쓰기위해==//
        TodoListAddForm todoListAddForm = new TodoListAddForm();
        todoListAddForm.setRegisterDate(date);
        model.addAttribute("addform", todoListAddForm);
        //==어디서든 날짜쓰려고 편의상 넣은 것==//
        model.addAttribute("date", date);

        //==아이디 가져오기==//
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //==contnet - TodoList 리스트 가져오기==//
        TodoList todoList = todoListRepository.findByDate(date, member);


        //==contnet - TodoListItem 리스트 가져오기 ==//
        List<TodoListItem> findItems = todoListItemRepository.findByDate(date,todoList);
        model.addAttribute("todoList",findItems);
        //==sidebar - TodoListItem==//
        List<TodoListItem> noSuccess = todoListItemRepository.findByNoSuccess(member);
        model.addAttribute("noSuccessTodoList",noSuccess);

        return "todolist";
    }


    @PostMapping("/todolist/move")
    public String moveTodoList(@RequestParam("date") String stringDate) {
        return "redirect:/todolist/" + stringDate;
    }

    @PostMapping("/todolist/{date}/add")
    public String addTodoListItem(@PathVariable("date") String stringDate, @ModelAttribute("form") TodoListAddForm form, HttpSession session) {
        if(form.getText()==null||form.getText().equals("")){
            return "redirect:/todolist/"+stringDate+"?error=titleNull";
        }
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
//        String stringDate = date.format(DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member findMember = memberRepository.findOne(member.getId());
        String text = form.getText();
        todoListItemService.join(date, member, text);
        return "redirect:/todolist/" + stringDate;
    }
    ///todolist/2022-09-07/11/edit?status1=61
    ///todolist/2022-09-07/11/edit?status2=34

    @GetMapping("/todolist/{date}/{todolistItemId}/{status}")
    public String editStatusTodoListItem(@PathVariable("date") String stringDate, @PathVariable("todolistItemId") String ItemId, @PathVariable("status") String rawStatus, Model model, HttpServletRequest request) {
    //    String status = rawStatus.substring(rawStatus.indexOf("="));
        Enumeration<String> parameterNames = request.getParameterNames();
        String key = parameterNames.nextElement();
        String parameter = request.getParameter(key);
        Long todolistItemId = Long.parseLong(ItemId);
        int statusPercentage = Integer.parseInt(parameter);
        //==itemid를 알면 다른사람이 todoListItem을 수정할 수 있음, 수정해야함==//
        todoListItemService.updatePercentage(todolistItemId,statusPercentage);
        return "redirect:/todolist/"+stringDate;
    }
    @GetMapping("/todolist/{date}/{todolistItemId}/delete")
    public String deleteTodoListItem(@PathVariable("date") String stringDate, @PathVariable("todolistItemId") String ItemId){
        Long todolistItemId = Long.parseLong(ItemId);
        todoListItemService.delete(todolistItemId);
        return "redirect:/todolist/"+stringDate;

    }
}
