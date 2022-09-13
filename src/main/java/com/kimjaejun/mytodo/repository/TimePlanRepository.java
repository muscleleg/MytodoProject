package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.SessionConst;
import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TimePlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TimePlanRepository {
    private final EntityManager em;

    public void save(TimePlan timePlan) {
        em.persist(timePlan);
    }

    public TimePlan findByText(TimePlan timePlan) {
        try {
            return em.createQuery("select t from TimePlan t where t.member=:member and t.title =:text", TimePlan.class)
                    .setParameter("member", timePlan.getMember())
                    .setParameter("text", timePlan.getTitle())
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public TimePlan findById(Long id) {
        return em.find(TimePlan.class, id);
    }

    public List<TimePlan> findAll(HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return em.createQuery("select t from TimePlan t where t.member =:member",TimePlan.class)
                .setParameter("member",member)
                .getResultList();
    }
}
