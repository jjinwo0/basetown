package jinwoo.basetown.entity;

import jinwoo.basetown.form.MemberForm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"id", "name"})
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    @Column(name = "team_name")
    private String name;

    private String city;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


    public Team(String name) {
        this.name = name;
    }

    public Team(String name, String city) {
        this.name = name;
        this.city = city;
    }
}
