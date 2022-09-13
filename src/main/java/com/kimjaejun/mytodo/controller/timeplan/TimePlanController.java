package com.kimjaejun.mytodo.controller.timeplan;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TimePlan;
import com.kimjaejun.mytodo.form.timeplan.TimePlanAddForm;
import com.kimjaejun.mytodo.form.todolist.TodoListAddForm;
import com.kimjaejun.mytodo.repository.TimePlanRepository;
import com.kimjaejun.mytodo.service.TimePlanService;
import com.kimjaejun.mytodo.utils.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimePlanController {
    private final TimePlanService timePlanService;
    private final TimePlanRepository timePlanRepository;
    @GetMapping("/timeplan")
    public String timeplan(Model model, HttpSession session, HttpServletRequest request) {
        List<TimePlan> findTimePlan = timePlanRepository.findAll(session);
        //==리스트 dto에 넣음==//
        List<TimePlanAddForm> timeplan = new ArrayList<>();
        for (TimePlan t : findTimePlan) {
            timeplan.add(new TimePlanAddForm(t.getTitle(),t.getRegisterDate()));
        }
        //==에러코드 추출==//

        String parameter = request.getParameter("error");
        if(parameter!=null && parameter.equals("DuplicateTitle")){
            model.addAttribute("error","DuplicateTitle");
        }
        model.addAttribute("timePlans", timeplan);
        model.addAttribute("timePlanAddForm", new TimePlanAddForm());
        return "timeplan";
    }
    @PostMapping("/timeplan/add")
    public String addTodoListItem(@ModelAttribute("timePlanAddForm") @Valid TimePlanAddForm timePlanAddForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "timeplan";
        }
//
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        TimePlan timePlan = TimePlan.createTimePlan(member,timePlanAddForm.getTitle(),LocalDate.now());
        try {
            timePlanService.join(timePlan);
        } catch (Exception e) {
            result.rejectValue("title","error.title","이미 추가된 계획입니다.");
            return "redirect:/timeplan?error=DuplicateTitle";
        }
        return "redirect:/timeplan";
    }

}
