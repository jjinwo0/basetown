package jinwoo.basetown.repository;

import jinwoo.basetown.form.MemberDto;
import jinwoo.basetown.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("select m from Member m where m.username = :username")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m.username from Member m")
    List<Member> findUsernameList();

    @Query("select new jinwoo.basetown.form.MemberDto(m.id, m.username, m.name, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username = :names")
    List<Member> findByNames(@Param("names")Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); //단건

    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    @Query("select m from Member m where m.id = :id")
    Member findById(Long id);

    @Query("select m from Member m where m.username = :username and m.password = :password")
    Member findByUsernameAndPassword(String username, String password);

    @Query("select m from Member m where m.password = :password")
    Member findByPassword(String password);

}