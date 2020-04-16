package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Member")    //default 클래스 명 -> JPA에서 사용할 엔티티 이름을 지정
//@Table(name = "Member")    //default 엔티티명 -> 매핑할 테이블 이름
//@SequenceGenerator(name = "member_sqe_generator", sequenceName = "member_sqe")
public class Member {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터베이스에 위임 ex) MySQL의 AUTO_ INCREMENT
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator") //유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 ex) Oracle Sequence
    //테이블 매핑 -> 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉 내내는 전략 -> 성능의 단점
    @GeneratedValue(strategy = GenerationType.AUTO) //DB방언에 따라 생성
    private Long id;

    @Column(name = "name", nullable = false)
    private String userName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private LocalDate testLocalDate;    //data type
    private LocalDateTime testLocalDateTime; //timestamp type으로 매핑됨

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public Member() {

    }
    public Member(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

}
