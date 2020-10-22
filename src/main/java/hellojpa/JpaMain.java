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
      Member member1 = new Member(150L, "A");
      Member member2 = new Member(160L, "B");

      // 쓰기 지연 SQL 저장소에 저장된다.
      entityManager.persist(member1);
      entityManager.persist(member2);
      System.out.println("------------");

      // 실제 쿼리가 날아간다.
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
