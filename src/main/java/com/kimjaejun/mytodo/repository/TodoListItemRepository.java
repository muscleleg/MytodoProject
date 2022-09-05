package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.TodoListItem;
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

    public List<TodoListItem> findByDate(LocalDate date) {
        List<TodoListItem> todoListItems = em.createQuery("select t from TodoListItem t where t.registerDate =: date",TodoListItem.class)
                .setParameter("date", date)
                .getResultList();
        return todoListItems;
    }
}
