package hellojpa;

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

  @OneToOne
  @JoinColumn(name = "LOCKER_ID")
  private Locker locker;

  @OneToMany(mappedBy = "member")
  private List<MemberProduct> memberProducts = new ArrayList<>();

  private Integer age;

  // DB에는 enum 타입이 없어서 이 애너테이션을 달아줘야 한다.
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  // 날짜 타입은 @Temporal을 달아준다.
  // DB는 DATE, TIME, TIMESTAMP로 나뉘기 때문에 정보를 줘야 한다.
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  // varchar를 넘어서는 큰 컨텐츠를 넣고 싶을 때 사용한다.
  // String 타입이면 DB에서 clob으로 생성된다.
  @Lob
  private String description;

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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
