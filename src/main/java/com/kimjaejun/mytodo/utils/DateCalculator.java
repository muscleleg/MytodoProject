package com.kimjaejun.mytodo.utils;

import com.kimjaejun.mytodo.domain.Member;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

@Getter
public class DateCalculator {
    private LocalDate start;
    private LocalDate end;
    private int thisYear = LocalDate.now().getYear();
    private int thisMonth = LocalDate.now().getMonthValue();
    public int getThisYear() {
        return LocalDate.now().getYear();
    }
    public int getThisMonth() {
        return LocalDate.now().getMonthValue();
    }
    public int getThisDay() {
        return LocalDate.now().getDayOfMonth();
    }
    //요번주//
    public void thisWeek() {
        LocalDate today = LocalDate.now();
        //1.월,2.화,3수
        int day = today.get(DAY_OF_WEEK);
        start = today.minusDays(day-1);
        end = start.plusDays( 6);
    }
    //요번달 시작일에서 끝
    public void thisMonth() {
        LocalDate today = LocalDate.now();
        //1.월,2.화,3수
        start = today.withDayOfMonth(1);
        end = today.withDayOfMonth(today.lengthOfMonth());
    }
    //해당 월의 시작일과 끝//
    public void month(int year,int month) {
        LocalDate today = LocalDate.of(thisYear, month, 1);
        //1.월,2.화,3수
        start = today;
        end = today.withDayOfMonth(today.lengthOfMonth());
    }
    public void day(int year,int month, int startDay, int endDay) {
        start = LocalDate.of(year, month, startDay);
        end = LocalDate.of(year, month, endDay);

    }

    public int calcEndDayFromMonth(int year, int month) {
        LocalDate calcMonth = LocalDate.of(year, month, 1);
        return calcMonth.withDayOfMonth(calcMonth.lengthOfMonth()).getDayOfMonth();
    }
    //==한 주의 시작일을 넣으면 그 주의 마지막일이 며칠인지 나옴==//
    public int calcEndDayOfWeek(int year, int month,int day) {
        LocalDate calcDay = LocalDate.of(year, month, day);
        int monthEnd= calcDay.withDayOfMonth(calcDay.lengthOfMonth()).getDayOfMonth();
        int end;
        if(day==1) {
            if (calcDay.get(DAY_OF_WEEK) < 7) {
                return 7 - calcDay.get(DAY_OF_WEEK);
            }
            return 7;
        }
        end = day + 6;
        if (end > monthEnd) {
            return monthEnd;
        }
        return end;
    }
    public List<Integer> getWeekFromMonth(int year, int month) {
        ArrayList<Integer> list = new ArrayList<>();
        DateCalculator dateCalculator = new DateCalculator();
        int len = dateCalculator.calcEndDayFromMonth(year,month);
        int endDay;
        int i=1;
        int count=0;
        while ( i <= len) {
            endDay = dateCalculator.calcEndDayOfWeek(year, month, i);
            i = 1 + endDay;
            list.add(++count);
        }
        return list;
    }
}
