package jinwoo.basetown.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class SigninForm {

    @NotEmpty(message = "필수 입력 항목입니다.")
    private String username;

    @NotEmpty(message = "필수 입력 항목입니다.")
    private String password;

}
