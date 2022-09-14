package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.TimePlan;
import com.kimjaejun.mytodo.repository.TimePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimePlanService {
    private final TimePlanRepository timePlanRepository;

    public void join(TimePlan timePlan) {
        validateDuplicateTodoList(timePlan);
        timePlanRepository.save(timePlan);
    }
    public TimePlan findById(Long id) {
        return timePlanRepository.findById(id);

    }
    private void validateDuplicateTodoList(TimePlan timePlan) {
        TimePlan finTimePlan = timePlanRepository.findByText(timePlan);
        if (finTimePlan!=null) {
            throw new IllegalStateException("이미 존재하는 계획입니다.");
        }
    }

    public void deleteByTimePlan(TimePlan timePlan) {
        timePlanRepository.remove(timePlan);
    }
}
