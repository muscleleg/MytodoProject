package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import com.kimjaejun.mytodo.utils.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoListItemRepository {
    private final EntityManager em;

    public void save(TodoListItem todoListItem) {
        em.persist(todoListItem);
    }

    public List<TodoListItem> findByDate(LocalDate date, TodoList todoList) {
        return em.createQuery("select t from TodoListItem t where t.registerDate =: date and t.todoList=:todoList",TodoListItem.class)
                .setParameter("date", date)
                .setParameter("todoList", todoList)
                .getResultList();

    }
    public List<TodoListItem> findByNoSuccess(Member member) {
         return em.createQuery("select t from TodoListItem t where t.member=:member and t.statusPercentage < 100 order by t.statusPercentage desc ",TodoListItem.class)
                .setParameter("member", member)
                .getResultList();

    }
    public List<TodoListItem> findByNoSuccessThisWeek(Member member) {
//        LocalDate today = LocalDate.now();
//        //1.월,2.화,3수
//        int day = today.get(DAY_OF_WEEK);
//        LocalDate start = today.minusDays(day-1);
//        LocalDate end = start.plusDays( 6);
        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.thisWeek();

        return em.createQuery("select t from TodoListItem t where t.member=:member and t.statusPercentage < 100 and t.registerDate >= :start and t.registerDate <=:end", TodoListItem.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getResultList();

    }
    public List<TodoListItem> findByThisWeek(Member member) {
//        LocalDate today = LocalDate.now();
//        //1.월,2.화,3수
//        int day = today.get(DAY_OF_WEEK);
//        LocalDate start = today.minusDays(day-1);
//        LocalDate end = start.plusDays( 6);
        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.thisWeek();

        return em.createQuery("select t from TodoListItem t where t.member=:member and t.registerDate >= :start and t.registerDate <=:end", TodoListItem.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getResultList();

    }
    public List<TodoListItem> findBySuccessThisWeek(Member member) {
//        LocalDate today = LocalDate.now();
//        //1.월,2.화,3수
//        int day = today.get(DAY_OF_WEEK);
//        LocalDate start = today.minusDays(day-1);
//        LocalDate end = start.plusDays( 6);
        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.thisWeek();

        return em.createQuery("select t from TodoListItem t where t.member=:member and t.statusPercentage = 100 and t.registerDate >= :start and t.registerDate <=:end", TodoListItem.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getResultList();

    }
    //원하는 년,월,일로 평균조회//
    public Double findBySuccessPercentageYearDay(Member member,int year,int month,int start,int end) {

        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.day(year,month,start,end);

        return em.createQuery("select AVG(t.statusPercentage) from TodoListItem t where t.member=:member and t.registerDate >= :start and t.registerDate <=:end", Double.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getSingleResult();
    }
    //==요번주 평균 메소드==//
    public Double findBySuccessPercentageThisWeek(Member member) {

        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.thisWeek();

        return em.createQuery("select AVG(t.statusPercentage) from TodoListItem t where t.member=:member and t.registerDate >= :start and t.registerDate <=:end", Double.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getSingleResult();
    }
    //==요번달 평균 메소드==//
    public Double findBySuccessPercentageThisMonth(Member member) {

        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.thisMonth();

        return em.createQuery("select AVG(t.statusPercentage) from TodoListItem t where t.member=:member and t.registerDate >= :start and t.registerDate <=:end", Double.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getSingleResult();
    }
    //==해당 월의 평균을 구하는 메소드//
    public Double findBySuccessPercentageMonth(Member member,int year,int month) {
        DateCalculator dateCalculator = new DateCalculator();
        dateCalculator.month(year,month);

        return em.createQuery("select AVG(t.statusPercentage) from TodoListItem t where t.member=:member and t.registerDate >= :start and t.registerDate <=:end", Double.class)
                .setParameter("member", member)
                .setParameter("start", dateCalculator.getStart())
                .setParameter("end", dateCalculator.getEnd())
                .getSingleResult();
    }
    @SuppressWarnings("unchecked")
    public List<TodoListItem> findByText(Member member, String text) {
        return em.createQuery("select t from TodoListItem t where t.member =: member and t.title like :text")
                .setParameter("member", member)
                .setParameter("text", "%"+text+"%")
                .getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<TodoListItem> findByMember(Member member){
        return  em.createQuery("select t from TodoListItem t where t.member =: member")
                .setParameter("member", member)
                .getResultList();


    }

        public TodoListItem findOne(Long todoListItemId) {
        return em.find(TodoListItem.class, todoListItemId);
    }
    public void remove(TodoListItem todoListItem){
        em.remove(todoListItem);
    }


}
