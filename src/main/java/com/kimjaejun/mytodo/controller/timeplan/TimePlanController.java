package com.kimjaejun.mytodo.controller.timeplan;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TimePlan;
import com.kimjaejun.mytodo.domain.TimePlanItem;
import com.kimjaejun.mytodo.dto.TimeTableDTO;
import com.kimjaejun.mytodo.form.timeplan.TimePlanAddForm;
import com.kimjaejun.mytodo.form.timeplan.TimePlanItemAddForm;
import com.kimjaejun.mytodo.repository.TimePlanItemRepository;
import com.kimjaejun.mytodo.repository.TimePlanRepository;
import com.kimjaejun.mytodo.service.TimePlanItemService;
import com.kimjaejun.mytodo.service.TimePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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


        //==에러 확인==//
        String parameter = request.getParameter("error");
        if(parameter!=null && parameter.equals("titleNull")){
            model.addAttribute("error","titleNull");
        }
        if(parameter!=null && parameter.equals("timeException")){
            model.addAttribute("error","timeException");
        }



        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //==url에 해당하는 timeplan찾기==//
        TimePlan findTimePlan = timePlanService.findById(id);
        if (findTimePlan == null || (member.getLoginId().equals(findTimePlan.getMember().getLoginId()))) {  //==본인일 경우만 접근가능, 본인이 아닌 사람이 접근할 경우 차단==//
            model.addAttribute("timePlan",findTimePlan);

            //==timeplan게시물이 가진 timplanitems조회==//
            List<TimePlanItem> findTimePlanItems = timePlanItemRepository.findByTimePlan(findTimePlan);
            model.addAttribute("timePlanItems", findTimePlanItems);

            //==timetalbe에 넣을 라벨배열 생성==//
            List<Integer> label = new ArrayList<>();
            for (int i = 1; i <= findTimePlanItems.size(); i++) {
                label.add(i);
            }
            model.addAttribute("label", label);
            //==timetalbe에 넣을 시작시간,종료시간생성==//
            int startHour;
            int startMin;
            int endHour;
            int endMin;
            int today;
            int nextDay;
            List<TimeTableDTO> data = new ArrayList<>();
            for (TimePlanItem findTimePlanItem : findTimePlanItems) {
                startHour = findTimePlanItem.getStartDate().getHour();
                startMin = findTimePlanItem.getStartDate().getMinute();
                endHour = findTimePlanItem.getEndDate().getHour();
                endMin = findTimePlanItem.getEndDate().getMinute();
                if( startHour >= 0 && endHour <24){

//                    if((startHour<=endHour)&&(endHour<=24)){
//
//                        data.add(new TimeTableDTO(startHour,startMin,endHour,endMin,1,1));
//                        continue;
//                    }
//                    if(endHour<6){
//                        data.add(new TimeTableDTO(startHour,startMin,endHour,endMin,1,2));
//                        continue;
//                    }
                    data.add(new TimeTableDTO(findTimePlanItem.getTitle(),startHour,startMin,endHour,endMin,1,1));
                }


            }
            model.addAttribute("data", data);
            TimePlanItemAddForm timePlanItemAddForm = new TimePlanItemAddForm();
            model.addAttribute("timePlanItemAddForm",timePlanItemAddForm);
            return "timeplanItem";
        }
        //==본인이 아닌경우 차단==//
        return "timeplan";
    }
    @PostMapping("/timeplan/add")
    public String addTimePlan(@RequestParam("title") String title, HttpSession session) {
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
    @GetMapping("/timeplan/delete/{id}")
    public String deleteTimePlan(@PathVariable("id") Long id,HttpSession session) {
        TimePlan findTimePlan = timePlanService.findById(id);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (!findTimePlan.getMember().getId().equals(member.getId())) {
            return "redirect:/timeplan";
        }
        timePlanService.deleteByTimePlan(findTimePlan);
        return "redirect:/timeplan";
    }

    @PostMapping("/timeplan/{id}/add")
    public String addTimeTableItem(@PathVariable("id")Long id,@RequestParam("title")String title,@RequestParam("startTime")String start,@RequestParam("endTime")String end, HttpSession session,Model model) {
        if(title.trim()==""){
            return "redirect:/timeplan/"+id+"?error=titleNull";

        }
        if (start == ""||end=="") {
            return "redirect:/timeplan/"+id+"?error=timeException";

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
        if(startHour==endHour&&endMin<=startMin){
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
    @GetMapping("/timeplan/{timeplanId}/delete/{timeplanItemId}")
    public String deleteTimePlan(@PathVariable("timeplanId") Long timePlanId, @PathVariable("timeplanItemId") Long timePlanItemId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        TimePlanItem findTimePlanItem = timePlanItemRepository.findById(timePlanItemId);
        TimePlan findTimePlan = timePlanRepository.findById(findTimePlanItem.getTimePlan().getId());
        if (!(member.getId().equals(findTimePlan.getMember().getId()))) { //잘못된 접근 차단//
            return "redirect:/timeplan";
        }
        timePlanItemService.delete(findTimePlanItem);
        return "redirect:/timeplan/"+timePlanId;
    }


}
