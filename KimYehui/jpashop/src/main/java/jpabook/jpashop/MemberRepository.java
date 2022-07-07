package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository //일종의 DAO
public class MemberRepository {

    @PersistenceContext //엔티티 매니저
    EntityManager em;

    public Long save(Member member){ //커맨드랑 쿼리를 분리해라! 그래서 쿼리의 리턴값을 최소화하는 편이다. 그래서 member가 아닌 id값만 리턴함
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
