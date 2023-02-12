package jinwoo.basetown.form;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class JoinMercForm {

    @NotEmpty(message = "필수 입력 항목입니다.")
    @Column(name = "team_name")
    private String TeamName;

    private String name;
    private int age;
    private String address;
    private String position;
}
