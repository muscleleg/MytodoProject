package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StaticsServiceTest {

    @Autowired
    StaticsService staticsService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 전체월테스트() {
        Member member = memberRepository.findByLogin("test", "test");
        List<Double> monthAverage = staticsService.getMonthAverage(member,2022);
        System.out.println("monthAverage = " + monthAverage);
    }
    @Test
    public void 주차별평균테스트() {
        Member member = memberRepository.findByLogin("test", "test");
        List<Double> weekAverage = staticsService.getWeekAverage(member,2022,8);
        System.out.println("weekAverage = " + weekAverage);
    }

}