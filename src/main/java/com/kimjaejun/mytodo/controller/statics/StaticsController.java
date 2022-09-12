package com.kimjaejun.mytodo.controller.statics;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.service.StatisticsService;
import com.kimjaejun.mytodo.utils.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaticsController {
    private final StatisticsService statisticsService;
    private final TodoListItemRepository todoListItemRepository;
    @GetMapping("/statistics")
    public String statistics(HttpSession session, Model model){
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        DateCalculator dateCalculator = new DateCalculator();
        //==이번주 통계==//
        List<TodoListItem> thisWeek = todoListItemRepository.findByThisWeek(member);
        List<Integer> thisWeekPercentage = new ArrayList<>();
        for (TodoListItem i : thisWeek) {
            thisWeekPercentage.add(i.getStatusPercentage());
        }
        dateCalculator.thisWeek();
        model.addAttribute("startDay", dateCalculator.getStart());
        model.addAttribute("endDay", dateCalculator.getEnd());
        model.addAttribute("thisWeekPercentage", thisWeekPercentage);

        //==주차별 통계==//

        List<Double> monthWeekAverage = statisticsService.getWeekAverage(member, dateCalculator.getThisYear(), dateCalculator.getThisMonth());
        model.addAttribute("month", dateCalculator.getThisMonth());
        model.addAttribute("monthWeekAverage", monthWeekAverage);
        model.addAttribute("monthWeekLabel", dateCalculator.getWeekFromMonth(dateCalculator.getThisYear(), dateCalculator.getThisMonth()));

        //==월병 평균==//
        List<Double> yearAverage = statisticsService.getMonthAverage(member, dateCalculator.getThisYear());
        model.addAttribute("year", dateCalculator.getThisYear());
        model.addAttribute("yearAverage", yearAverage);


        return "statistics";
    }
}
