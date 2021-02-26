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
    EntityManager em = entityManagerFactory.createEntityManager();

    // JPA의 모든 작업은 트랜잭션 내에서 해야한다.
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      Member member = new Member();
      member.setUsername("member1");
      member.setHomeAddress(new Address("home city", "street", "12345"));

      member.getFavoriteFoods().add("치킨");
      member.getFavoriteFoods().add("족발");
      member.getFavoriteFoods().add("피자");

      member.getAddressHistory().add(new Address("old1", "street", "12345"));
      member.getAddressHistory().add(new Address("old2", "street", "12345"));

      em.persist(member);

      // DB에는 데이터가 있고 애플리케이션을 아예 처음부터 시작하는 것처럼 실행할 수 있다.
      em.flush();
      em.clear();

      System.out.println("=====================");
      Member findMember = em.find(Member.class, member.getId());

      // 값 타입은 immutable 해야 하기 때문에 이렇게 변경하면 절대 안된다.
//      findMember.getHomeAddress().setCity("new city");

      // address 인스턴스 자체를 갈아끼워야 한다.
      Address a = findMember.getHomeAddress();
      findMember.setHomeAddress(new Address("new city", a.getStreet(), a.getZipcode()));

      // 단순 String이기 때문에 지우고 넣어야 한다.
      // String 자체가 값 타입이기 때문에 통으로 갈아 끼우는 것 말고는 방법이 없다. 따로 업데이트가 불가하다.
      findMember.getFavoriteFoods().remove("치킨");
      findMember.getFavoriteFoods().add("한식");

      // address를 하나만 바꾸고 싶다면
      // 기본적으로 컬렉션은 대부분 대상을 찾을 때 equals()로 동작한다.
      // 그래서 찾고 싶은 값을 그대로 넣어주면 그 값을 찾아준다.
      // 따라서 equals()를 재정의 하지 않았다면 그냥 망하는 것이다. equals()를 꼭 재정의 해주자.
      findMember.getAddressHistory().remove(new Address("old1", "street", "12345"));
      // 지운 값 대신 새로운 값을 넣어준다.
      findMember.getAddressHistory().add(new Address("new city", "street", "12345"));

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      // 엔티티 매니저가 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 쓰고 나서 꼭 닫아줘야 한다.
      em.close();
    }

    // 전체 애플리케이션이 끝나면 팩토리까지 닫아준다.
    entityManagerFactory.close();
  }
}
