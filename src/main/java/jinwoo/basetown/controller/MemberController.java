package jinwoo.basetown.controller;

import jinwoo.basetown.dto.AuthForm;
import jinwoo.basetown.dto.MemberDto;
import jinwoo.basetown.dto.MemberForm;
import jinwoo.basetown.dto.SigninForm;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import jinwoo.basetown.service.MemberService;
import jinwoo.basetown.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //회원 가입 폼 매핑
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    //회원 가입 매핑
    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if (result.hasErrors())
            return "members/createMemberForm";

        memberService.join(form);

        return "redirect:/";
    }

    //회원 조회 폼 매핑
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

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

        Member loginMember = memberService.signin(form.getUsername(), form.getPassword());

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

    //회원 수정을 위한 회원 인증 폼
    @GetMapping("/members/modify/auth")
    public String authForModifyForm(Model model){
        model.addAttribute("authForm", new AuthForm());
        return "/members/authForModifyForm";
    }

    //회원 수정을 위한 회원 인증 매핑
    @PostMapping("/members/modify/auth")
    public String authForModifyForm(@Valid @ModelAttribute("authForm") AuthForm form, BindingResult result){
        if (result.hasErrors()){
            log.info("에러 발생");
            return "members/authForModifyForm";
        }

        Member authMember = memberService.auth(form);
        if (authMember == null){
            log.info("존재하지 않는 회원입니다.");
            return "members/authForModifyForm";
        }

        return "redirect:/members/modify";

    }


    @GetMapping("/members/modify")
    public String modifyForm(Model model){
        model.addAttribute("modifyForm", new MemberForm());
        return "/members/modifyMemberForm";
    }

    @PostMapping("/members/modify")
    public String modify(@Valid MemberForm form, @SessionAttribute("loginMember") Member member, BindingResult result){
        if (result.hasErrors()){
            log.info("에러 발생");
            return "members/modifyMemberForm";
        }

        memberService.modify(member.getId(), form);

        return "redirect:/";
    }
}
