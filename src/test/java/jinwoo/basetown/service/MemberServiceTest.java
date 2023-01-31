package jinwoo.basetown.service;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    MemberService memberService;
    
    @PersistenceContext
    EntityManager em;
    
    @Test
    public void 회원가입(){
        Member member = new Member("memberA", 20, "Seoul", "SP", null);

        Long joinId = memberService.join(member);

        em.flush();

        assertThat(member.getId()).isEqualTo(joinId);
    }

    @Test
    public void 중복_회원_예외(){
        Member member1 = new Member("memberA");
        Member member2 = new Member("memberA");

        memberService.join(member1);
        memberService.join(member2);

        fail("중복 회원 가입 시, 예외가 발생해야 함.");
    }
}
