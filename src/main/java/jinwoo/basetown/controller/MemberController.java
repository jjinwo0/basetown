package jinwoo.basetown.controller;

import jinwoo.basetown.dto.MemberDto;
import jinwoo.basetown.dto.MemberForm;
import jinwoo.basetown.dto.SigninForm;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import jinwoo.basetown.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if (result.hasErrors())
            return "members/createMemberForm";

        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAddress(form.getAddress());
        member.setAge(form.getAge());
        member.setPosition(form.getPosition());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

    @GetMapping("/members/signin")
    public String signInForm(Model model){
        model.addAttribute("signInForm", new SigninForm()); //SigninForm 정보를 signInForm 이라는 이름으로 전달
        return "members/signinForm";
    }

    @PostMapping("members/signin")
    public String signIn(@Valid SigninForm form, BindingResult result, HttpServletResponse response){
        if (result.hasErrors()){
            log.info("에러 발생");
            return "members/signinForm";
        }

        Member member = memberRepository.findByUsernameAndPassword(form.getUsername(), form.getPassword());
        if (member.equals(null)){
            log.info("해당하는 정보의 멤버가 없습니다.");
            result.reject("Signin Fail", "일치하는 정보가 없습니다.");
            return "members/signinForm";
        }else {
            Cookie cookie = new Cookie("username", member.getUsername());
            response.addCookie(cookie);
        }

        return "redirect:/";
    }
}
