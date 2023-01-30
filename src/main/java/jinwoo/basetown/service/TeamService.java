package jinwoo.basetown.service;

import jinwoo.basetown.entity.Team;
import jinwoo.basetown.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
}
