package hellojpa;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
// 싱글 테이블에 다 모아두는 기본 전략에서 JOINED로 바꿔준다.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// DTYPE이 추가된다.
@DiscriminatorColumn
// 추상클래스여야 상속과 상관없이 독단적으로 사용하는 걸 막을 수 있다.
public abstract class Item {
  @Id @GeneratedValue
  private Long id;

  private String name;
  private int price;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
