package com.kimjaejun.mytodo.controller.timeplan;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TimePlan;
import com.kimjaejun.mytodo.domain.TimePlanItem;
import com.kimjaejun.mytodo.form.timeplan.TimePlanAddForm;
import com.kimjaejun.mytodo.form.timeplan.TimePlanItemAddForm;
import com.kimjaejun.mytodo.repository.TimePlanItemRepository;
import com.kimjaejun.mytodo.repository.TimePlanRepository;
import com.kimjaejun.mytodo.service.TimePlanItemService;
import com.kimjaejun.mytodo.service.TimePlanService;
import com.kimjaejun.mytodo.utils.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimePlanController {
    private final TimePlanService timePlanService;
    private final TimePlanRepository timePlanRepository;

    private final TimePlanItemRepository timePlanItemRepository;
    private final TimePlanItemService timePlanItemService;
    @GetMapping("/timeplan")
    public String allTimeplan(Model model, HttpSession session, HttpServletRequest request) {
        List<TimePlan> findTimePlan = timePlanRepository.findAll(session);
        //==리스트 dto에 넣음==//
        List<TimePlanAddForm> timeplan = new ArrayList<>();
        for (TimePlan t : findTimePlan) {
            timeplan.add(new TimePlanAddForm(t.getId(),t.getTitle(),t.getRegisterDate()));
        }
        //==에러코드 추출==//

        String parameter = request.getParameter("error");
        if(parameter!=null && parameter.equals("DuplicateTitle")){
            model.addAttribute("error","DuplicateTitle");
        }
        if(parameter!=null && parameter.equals("null")){
            model.addAttribute("error","null");
        }
        model.addAttribute("timePlans", timeplan);
        model.addAttribute("timePlanAddForm", new TimePlanAddForm());
        return "timeplan";
    }

    @GetMapping("/timeplan/{id}")
    public String timeplanByTitle(@PathVariable("id") Long id,Model model,HttpSession session,HttpServletRequest request) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        TimePlan findTimePlan = timePlanService.findById(id);

        String parameter = request.getParameter("error");
        if(parameter!=null && parameter.equals("timeException")){
            model.addAttribute("error","timeException");
        }
        if(parameter!=null && parameter.equals("titleNull")){
            model.addAttribute("error","titleNull");
        }
        if (findTimePlan == null || (member.getLoginId().equals(findTimePlan.getMember().getLoginId()))) {
            model.addAttribute("timePlan",findTimePlan);

            List<TimePlanItem> findTimePlanItems = timePlanItemRepository.findByTimePlan(findTimePlan);
            model.addAttribute("timePlanItems", findTimePlanItems);

            TimePlanItemAddForm timePlanItemAddForm = new TimePlanItemAddForm();
            model.addAttribute("timePlanItemAddForm",timePlanItemAddForm);
            return "timeplanItem";
        }
        return "timeplan";
    }
    @PostMapping("/timeplan/add")
    public String addTimeTable(@RequestParam("title") String title, HttpSession session) {
        if (title=="") {
            return "redirect:/timeplan?error=null";
        }
//
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        TimePlan timePlan = TimePlan.createTimePlan(member,title,LocalDate.now());
        try {
            timePlanService.join(timePlan);
        } catch (Exception e) {
//            result.rejectValue("title","error.title","이미 추가된 계획입니다.");
            return "redirect:/timeplan?error=DuplicateTitle";
        }
        return "redirect:/timeplan";
    }
    @PostMapping("/timeplan/{id}/add")
    public String addTimeTable(@PathVariable("id")Long id,@RequestParam("title")String title,@RequestParam("startTime")String start,@RequestParam("endTime")String end, HttpSession session,Model model) {
        if (start == ""||end=="") {
            return "redirect:/timeplan/"+id+"?error=timeException";

        }
        if(title.trim()==""){
            return "redirect:/timeplan/"+id+"?error=titleNull";

        }
        int startHour = Integer.parseInt(start.substring(0, start.indexOf(':')));
        int startMin  = Integer.parseInt(start.substring(start.indexOf(':')+1,start.length()));
        LocalTime startTime = LocalTime.of(startHour,startMin);

        int endHour = Integer.parseInt(end.substring(0, end.indexOf(':')));
        int endMin  = Integer.parseInt(end.substring(end.indexOf(':')+1,end.length()));
        LocalTime endTime = LocalTime.of(endHour,endMin);
        if (startTime.isAfter(endTime)) {
            return "redirect:/timeplan/"+id+"?error=timeException";
        }

            Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            TimePlan timePlan = timePlanRepository.findById(id);
            if (member.getLoginId().equals(timePlan.getMember().getLoginId())){
                TimePlanItem timePlanItem = TimePlanItem.createTimePlanItem(timePlan, title, startTime, endTime);
                timePlanItemService.join(timePlanItem);
                return "redirect:/timeplan/"+id;
            }


        return "redirect:/timeplan/";
    }

}
