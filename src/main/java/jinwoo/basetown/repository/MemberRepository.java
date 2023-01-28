package jinwoo.basetown.repository;

import jinwoo.basetown.dto.MemberDto;
import jinwoo.basetown.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("select m from Member m where m.username = :username")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<Member> findUsernameList();

    @Query("select new jinwoo.basetown.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username = :names")
    List<Member> findByNames(@Param("names")Collection<String> names);


}