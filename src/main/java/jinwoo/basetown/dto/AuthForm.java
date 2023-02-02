package jinwoo.basetown.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AuthForm {
    @NotEmpty(message = "필수 입력 항목입니다.")
    private String username;
    private String password;

    public AuthForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
