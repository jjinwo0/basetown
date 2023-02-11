package jinwoo.basetown.controller;

import jinwoo.basetown.dto.*;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import jinwoo.basetown.service.MemberService;
import jinwoo.basetown.service.TeamService;
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

    //팀 가입 폼 매핑
    @GetMapping("/members/team/join")
    public String joinTeamForm(Model model){
        model.addAttribute("joinTeamForm", new JoinTeamForm());
        return "/team/joinTeamForm";
    }

    @PostMapping("/members/team/join")
    public String joinTeam(@Valid @ModelAttribute("joinTeamForm") JoinTeamForm form, @SessionAttribute("loginMember")Member member, HttpServletRequest request){

        memberService.modifyForJoinTeam(member.getId(), form);

        return "redirect:/";
    }
}
