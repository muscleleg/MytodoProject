package com.kimjaejun.mytodo.controller;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.repository.TodoListRepository;
import com.kimjaejun.mytodo.utils.DayCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TodoListItemRepository todoListItemRepository;
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if (session == null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null) {
            return "home";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<TodoListItem> findItems = todoListItemRepository.findByNoSuccessThisWeek(member);

        DayCalculator dayCalculator = new DayCalculator();
        dayCalculator.thisWeek();
        model.addAttribute("startDay",dayCalculator.getStart());
        model.addAttribute("endDay", dayCalculator.getEnd());
        model.addAttribute("todoList",findItems);
        return "loginhome";
    }

    @GetMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
    @GetMapping("/timeplan")
    public String timeplan(){
        return "timeplan";
    }
    @GetMapping("/group")
    public String group(){
        return "group";
    }
}
