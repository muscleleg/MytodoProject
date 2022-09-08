package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import com.kimjaejun.mytodo.domain.TodoListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

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
        LocalDate today = LocalDate.now();
        int day = today.get(DAY_OF_WEEK);
        LocalDate start = today.minusDays(day);
        LocalDate end = start.plusDays(6);


        return em.createQuery("select t from TodoListItem t where t.member=:member and t.statusPercentage < 100 and t.registerDate between :start and :end", TodoListItem.class)
                .setParameter("member", member)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

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
