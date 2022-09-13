package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.TimePlanItem;
import com.kimjaejun.mytodo.repository.TimePlanItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimePlanItemService {
    private final TimePlanItemRepository timePlanItemRepository;

    public void join(TimePlanItem timePlanItem) {
        timePlanItemRepository.save(timePlanItem);
    }
}
