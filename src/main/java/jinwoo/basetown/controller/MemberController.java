package jinwoo.basetown.controller;

import jinwoo.basetown.dto.MemberDto;
import jinwoo.basetown.dto.MemberForm;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import jinwoo.basetown.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
        member.setUsername(form.getName());
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
}
