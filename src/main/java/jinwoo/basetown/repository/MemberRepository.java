package jinwoo.basetown.repository;

import jinwoo.basetown.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {


}