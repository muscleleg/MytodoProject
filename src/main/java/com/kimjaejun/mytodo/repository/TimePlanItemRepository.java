package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.TimePlan;
import com.kimjaejun.mytodo.domain.TimePlanItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimePlanItemRepository {
    private final EntityManager em;
    public void save(TimePlanItem timePlanItem){
        em.persist(timePlanItem);
    }

    public List<TimePlanItem> findByTimePlan(TimePlan timePlan) {
        return em.createQuery("select t from TimePlanItem t where t.timePlan=:timeplan")
                .setParameter("timeplan", timePlan)
                .getResultList();
    }
}
