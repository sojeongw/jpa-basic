package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

  public static void main(String[] args) {
    // persistence.xml에서 설정했던 unit-name을 넘긴다.
    // 엔티티 매니저 팩토리는 애플리케이션 로딩 시점에 딱 하나만 만들어야 한다.
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

    // 엔티티 매니저를 꺼낸다.
    // 엔티티 매니저는 트랜잭션마다 만들어줘야 한다.
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // JPA의 모든 작업은 트랜잭션 내에서 해야한다.
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();

    try {
      /*  회원 생성
      Member member = new Member();
      member.setId(1L);
      member.setName("helloA");

      // 저장한다.
      entityManager.persist(member); */


      /* 회원 수정
      // 수정할 대상을 가져온다.
      Member findMember = entityManager.find(Member.class, 1L);
      System.out.println("id: " + findMember.getId());
      System.out.println("name: " + findMember.getName());

      findMember.setName("helloJPA");

      // 수정한 객체를 따로 저장하지 않아도 된다.
      // 데이터를 JPA를 통해 가져오면 변경 여부를 트랜잭션 커밋 시점에
      // 다 체크해서 바뀐 내용에 대해 업데이트 쿼리를 만들어 날린다.*/


      // JPA는 테이블 대상이 아니라 객체를 대상으로 쿼리를 짠다.
      // 따라서 Member는 테이블이 아니라 객체를 가리킨다.
      List<Member> result = entityManager
          .createQuery("select m from Member as m", Member.class)
          // 페이지네이션을 할 수도 있다. 아래는 1번부터 10개 가져온다.
          .setFirstResult(1)
          .setMaxResults(10)
          .getResultList();

      for (Member member : result) {
        System.out.println("name: " + member.getName());
      }

      // 커밋한다.
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      // 엔티티 매니저가 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 쓰고 나서 꼭 닫아줘야 한다.
      entityManager.close();
    }

    // 전체 애플리케이션이 끝나면 팩토리까지 닫아준다.
    entityManagerFactory.close();
  }
}
