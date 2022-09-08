package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.domain.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
@RequiredArgsConstructor
public class TodoListRepository {
    private final EntityManager em;

    public void save(TodoList todoList) {
        em.persist(todoList);
    }
    public TodoList findByDate(LocalDate date,Member member){
//        LocalDate date = LocalDate.parse(date, DateTimeFormatter.ofPattern("[yyyy-MM-dd]"));

        try {
            TodoList findTodoList = em.createQuery("select t from TodoList t where t.registerDate =: date and t.member=:member", TodoList.class)
                    .setParameter("date", date)
                    .setParameter("member", member)
                    .getSingleResult();
            return findTodoList;
        }catch(NoResultException e){
            return null;
        }
    }
    public TodoList findByMemberId(Member member){
        try {
            TodoList findTodoList = em.createQuery("select t from TodoList t where t.member =: member", TodoList.class)
                    .setParameter("member", member)
                    .getSingleResult();
            return findTodoList;
        }catch(NoResultException e){
            return null;
        }
    }
}
