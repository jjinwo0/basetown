package jinwoo.basetown.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
public class MemberForm {

    @NotEmpty(message = "필수 입력 항목입니다.")
    private String name;

    @NotEmpty(message = "필수 입력 항목입니다.")
    private String username;
    private String password;
    private String address;
    private int age;
    private String position;

    public MemberForm(String username) {
        this.username = username;
    }

    public MemberForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MemberForm(String name, String username, String password, String address, int age, String position) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.age = age;
        this.position = position;
    }
}
