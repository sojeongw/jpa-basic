package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

  @Id @GeneratedValue
  private Long id;

  private String name;

  // 일대일에도 연관 관계 주인은 똑같이 적용될 수 있다.
  @OneToOne(mappedBy = "locker")
  private Member member;
}
