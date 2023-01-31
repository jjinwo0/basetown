package jinwoo.basetown.controller;

import jinwoo.basetown.dto.TeamForm;
import jinwoo.basetown.entity.Team;
import jinwoo.basetown.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    @GetMapping("/team/new")
    public String createTeam(Model model){
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
}
