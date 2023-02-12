package jinwoo.basetown.service;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.entity.Team;
import jinwoo.basetown.entity.TeamRole;
import jinwoo.basetown.form.JoinMercForm;
import jinwoo.basetown.repository.TeamRepository;
import jinwoo.basetown.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void saveTeam(Team team){
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long id, String name, String city){
        Team team = teamRepository.findOne(id);

        team.setName(name);
        team.setCity(city);
    }

    public List<Team> findTeam(){
        return teamRepository.findAll();
    }

    public Team findOne(Long id){
        return teamRepository.findOne(id);
    }

    @Transactional
    public void joinMerc(HttpSession session, @Valid JoinMercForm form){

        Member sessionMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Team findTeam = teamRepository.findTeamByName(form.getName());


        sessionMember.setLastJoinTeamName(form.getTeamName());
        sessionMember.setTeamRole(TeamRole.MERCENARY);
    }
}
