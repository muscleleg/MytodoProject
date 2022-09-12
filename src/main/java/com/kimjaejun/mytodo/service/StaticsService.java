package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.repository.TodoListItemRepository;
import com.kimjaejun.mytodo.utils.DayCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StaticsService {

    private final TodoListItemRepository todoListItemRepository;

    public List<Double> getMonthAverage(Member member,int year) {

        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Double result = todoListItemRepository.findBySuccessPercentageMonth(member, year,i + 1);
            list.add(result);
        }
        return list;
    }
    //이번 주의 통계
    public Double getThisWeekAverage(Member member,int year, int month) {
        Double result = todoListItemRepository.findBySuccessPercentageThisWeek(member);
        return result;
    }

        //원하는 연도의 주차별 통계//
    public List<Double> getWeekAverage(Member member,int year, int month) {
        ArrayList<Double> list = new ArrayList<>();
        LocalDate date;
        DayCalculator dayCalculator = new DayCalculator();
        int len = dayCalculator.calcEndDayFromMonth(year,month);
        int endDay;
        int i=1;
        while ( i <= len) {
            endDay = dayCalculator.calcEndDayOfWeek(year, month, i);
            Double result = todoListItemRepository.findBySuccessPercentageYearDay(member,year,month,i,endDay);
            i = 1 + endDay;
            list.add(result);

        }
        return list;
    }

    }
