package jinwoo.basetown.repository;

import jinwoo.basetown.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findListByName(String name);
    Team findTeamByName(String name);
    Optional<Team> findOptionalByName(String name);

    @Query("select t from Team t where t.id = :id")
    Team findOne(Long id);
}
