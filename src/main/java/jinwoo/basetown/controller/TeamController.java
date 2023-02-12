package jinwoo.basetown.controller;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.form.JoinMercForm;
import jinwoo.basetown.form.TeamForm;
import jinwoo.basetown.entity.Team;
import jinwoo.basetown.repository.TeamRepository;
import jinwoo.basetown.service.TeamService;
import jinwoo.basetown.session.SessionConst;
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
public class TeamController {

    private final TeamRepository teamRepository;
    private final TeamService teamService;

    @GetMapping("/team/new")
    public String createForm(Model model){
        model.addAttribute("teamForm", new TeamForm());
        return "team/createTeamForm";
    }

    @PostMapping("/team/new")
    public String create(TeamForm form){

        Team team = new Team();
        team.setName(form.getName());
        team.setCity(form.getCity());

        teamRepository.save(team);
        return "redirect:/";
    }

    @GetMapping("/team/mercenary")
    public String joinMercForm(Model model){
        model.addAttribute("joinMercForm", new JoinMercForm());
        return "/team/joinMercForm";
    }

    @PostMapping("/team/mercenary")
    public String joinMerc(@Valid JoinMercForm form, BindingResult result, HttpServletRequest request){
        if (result.hasErrors()){
            log.info("에러 발생");
            return "/team/joinMercForm";
        }

        HttpSession session = request.getSession(false);
        if (session == null){
            log.info("세션에 값이 존재하지 않습니다.");
            return "/team/joinMercForm";
        }

        teamService.joinMerc(session, form);

        return "redirect:/";
    }
}
