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

            /*
            //insert
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);
            */

            /*
            //select
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.id = " + findMember.getName());
            */

            /*
            //delete
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
            */

            /*
            //update
            //JPA를 통해서 가지고온 객체 -> 영속성 객체
            //영속성 객체이기 때문에 저장을 따로 수행하지 않아도 JPA가 업데이트를 수행함
            //commit 수행전 변경사항에 대한 업데이트 쿼리를 자동 생성해서 수행함
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");
            */

            /*
            //조건에 해당하는 조회 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)  // 1번부터
                    .setMaxResults(10)  // 10개
                    .getResultList();

            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }
            */


            /*
            //비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            //영속
            System.out.println("=== BEFORE ===");
            //디비에 저장하는 것이 아니라 영속성 컨텍스트라는 곳에 저장하는 것으로 여기서 쿼리가 날라가지 않음
            em.persist(member);
            System.out.println("=== AFTER ===");

            //1차 캐시에서 존재할 경우 1차 캐시에서 불러오게 된다
            //없는 경우 DB에서 조회후 1차 캐시에 저장 후 반환
            //1차 캐시의 경우 트랜잭션 처리 단위에서 엔티티메니저가 생성되고 지워지기 때문에 성능상의 큰 이점은 없음
            //실제 어플리케이션 전체에서 사용할 수 있는 캐시의 경우 JPA/Hibernate의 2차 캐시를 의미함

            //조회 쿼리가 실행되지 않음
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.id = " + findMember.getName());
            */

            /*
            //첫번째 find에서는 쿼리 실행, 2번째에서는 1차 캐시
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            //영속 엔티티의 동일성 보장
            System.out.println("result = " + (findMember1 == findMember2));
            */

            /*
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            //여기까지 Insert Sql을 데이터베이스에 보내지 않음
            //커밋하는 순간 데이터베이스 Insert Sql을 보냄
            //버퍼링을 모아서 한번에 쿼리를 수행할 수 있는 이점을 얻을 수 있음
            System.out.println("===================");
            */

            /*
            //수정 -> update
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");
            //영속성 객체의 경우 바뀐 데이터의 더티 체킹을 통해 수정된 사항을 자동 업데이트
            */

            /*
            Member member = new Member(200L, "member200");
            em.persist(member);
            em.flush();
            //flush 강제로 수행 -> 즉시 쿼리 수행(commit()전에)
            //flush수행해도 1차 캐시 삭제되지 않음, 단지 쓰기 지연 SQL저장소에 있는 것들을 데이터에비스 반영되는 것
            System.out.println("===================");
            */

            //영속성 없애기
            /*
            Member member = em.find(Member.class, 150L);
            member.setName("AAAA");

            em.detach(member);  //jpa에서 관리하지 않음 -> 영속성 컨텍스트에서 특정 엔티티를 빼버림
            em.clear(); //영속성 컨텍스트에서 모든 엔티티 삭제 -> 1차 캐시 모두 삭제됨

            System.out.println("===================");
            */

            //Identity전략의 경우 예외적으로 .persist 순간에 쿼리를 날려버림 -> 키를 통해 영속성 유지를 위해서
            Member member = new Member();
            member.setName("C");
            System.out.println("===================");
            em.persist(member); //쿼리 실행후 -> jpa가 id값을 세팅 -> 영속성 컨텍스트의 pk값으로 사용
            System.out.println("member.id = " + member.getId());
            System.out.println("===================");

            //나머지는 insert쿼리가 실행되지는 않고 key값만을 db에서 가져와서 영속성 컨텍스트를 만들어줌
            //sequence Generator, TableGenerator
            //allocationSize = 50의 경우
            //DB에서 미리 50개를 inc 시킨 이후 -> 메모리 상에서 50개를 사용할 수 있음
            //50개를 다 쓰기 전에는 call next value를 하지 않기 때문에 성능 최적화에 도움
            //너무 큰 수를 사용하면 그 만큼 빈 숫자가 많이 생김 -> 적당히 50, 100개

            tx.commit();    //이 시점에 영속성 컨텍스트에 있는 것들에 대해서 쿼리가 날라감
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();;
        }

        emf.close();
    }
}
