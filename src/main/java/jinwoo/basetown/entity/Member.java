package jinwoo.basetown.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "username", "age"})
public class Member extends TimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    @NotEmpty
    private String username;
    private String password;
    private int age;
    private String address;
    private String position;
    private Role role;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    //Constructor
    public Member(String username) {
        this.username = username;
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member(String name, String username, String password, int age, String address, String position) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.address = address;
        this.position = position;
    }

    public Member(String username, int age, String address, String position) {
        this.username = username;
        this.age = age;
        this.address = address;
        this.position = position;
    }

    public Member(String username, String password, int age, String address, String position, Team team) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.address = address;
        this.position = position;
        this.team = team;
    }

    public Member(String username, int age, String address, String position, Team team) {
        this.username = username;
        this.age = age;
        this.address = address;
        this.position = position;
        this.team = team;
    }

    //연관관계 메서드
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
