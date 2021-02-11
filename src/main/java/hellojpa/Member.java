package hellojpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

// JPA가 사용하는 데이터라고 인식한다.
@Entity
@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
  private Long id;

  @Column(name = "name")
  private String username;

  public boolean isWork() {
    // 응집성 있는 로직 구현
    return true;
  }

  @Embedded
  private Period period;

  @Embedded
  private Address address;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "work_city")),
      @AttributeOverride(name = "street", column = @Column(name = "work_street")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
  })
  private Address workAddress;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
