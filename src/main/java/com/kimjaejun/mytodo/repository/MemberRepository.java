package com.kimjaejun.mytodo.repository;

import com.kimjaejun.mytodo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * 싱글톤에 들어있는 EntityManger em을 쓰겠다는것
     * @Autowired
     * private EntityManager em;
     *
     * public MemberRepository(EntityManager entityManager){
     *     this.EntityManager = entityManager
     * }
     */

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =: name", Member.class)
                .setParameter("name", name)
                .getResultList();

    }
    public Member findByLoginId(String loginId) {
        try {
            Member findMemberId = em.createQuery("select m from Member m where m.loginId =: loginId", Member.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult();
            return findMemberId;
        }catch(NoResultException e){
            return null;
        }

    }

    public Member findByLogin(String loginId, String password) {
        try {
            return em.createQuery("select m from Member m where m.loginId =:loginId and m.password =:password", Member.class)
                    .setParameter("loginId", loginId)
                    .setParameter("password", password).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
