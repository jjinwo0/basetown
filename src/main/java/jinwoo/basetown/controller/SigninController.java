package jinwoo.basetown.controller;

import jinwoo.basetown.dto.SigninForm;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.service.MemberService;
import jinwoo.basetown.service.SigninService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SigninController {

    private final MemberService memberService;

    private final SigninService signinService;

    //회원 로그인 폼 매핑
    @GetMapping("/signin")
    public String signInForm(Model model){
        model.addAttribute("signInForm", new SigninForm()); //SigninForm 정보를 signInForm 이라는 이름으로 전달
        return "members/signinForm";
    }

    //회원 로그인 매핑
    @PostMapping("/signin")
    public String signIn(@Valid SigninForm form, BindingResult result, HttpServletRequest request){
        if (result.hasErrors()){
            log.info("에러 발생");
            return "members/signinForm";
        }

        Member loginMember = signinService.signin(form.getUsername(), form.getPassword());

        if (loginMember.equals(null)){
            log.info("해당하는 정보의 멤버가 없습니다.");
            result.reject("Signin Fail", "일치하는 정보가 없습니다.");
            return "members/signinForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성 및 반환
        //getSession(false): 세션이 없을 때 새로 만들지 않고 return null
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginMember);

        return "redirect:/";
    }


    //로그아웃 매핑
    @GetMapping("/signout")
    public String logout(HttpServletRequest request){
        //세션이 없더라도 생성되면 안되기 때문에 false 사용
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
