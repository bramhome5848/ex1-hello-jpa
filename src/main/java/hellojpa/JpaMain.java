package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] arge) {

        //persistence.xml 파일에서 unit-name
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            //모든 데이터를 변경하는 모든 작업은 JPA에서 트랜잭션 안에서 작업해야함
            //엔티티 매니저는 쓰레드간 공유X -> 사용하고 버려야
            //insert
            /*
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);
            */

            //select
            /*
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.id = " + findMember.getName());
            */

            //delete
            /*
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
            */

            //update
            //JPA를 통해서 가지고온 객체 -> 영속성 객체
            //영속성 객체이기 때문에 저장을 따로 수행하지 않아도 JPA가 업데이트를 수행함
            //commit 수행전 변경사항에 대한 업데이트 쿼리를 자동 생성해서 수행함
            /*
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");
            */

            //조건에 해당하는 조회 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)  // 1번부터
                    .setMaxResults(10)  // 10개
                    .getResultList();

            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();;
        }

        emf.close();
    }
}
