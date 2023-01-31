package jinwoo.basetown.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String username;
    private String password;
    private int age;
    private String address;
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

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

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
