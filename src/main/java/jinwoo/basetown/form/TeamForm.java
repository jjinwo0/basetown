package jinwoo.basetown.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class TeamForm {

    @NotEmpty(message = "필수 입력 항목입니다.")
    @Column(name = "team_name")
    private String name;
    private String city;
    private int personNum;
}
