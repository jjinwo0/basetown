package jinwoo.basetown.service;

import jinwoo.basetown.dto.MemberForm;
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
        MemberForm formMember = new MemberForm("memberA", "asdf", "1234", "Seoul", 23, "SP");

        Long joinId = memberService.join(formMember);
        Member findMember = memberRepository.findById(joinId);

        em.flush();

        assertThat(findMember.getId()).isEqualTo(joinId);
    }

    @Test
    public void 중복_회원_예외(){
        MemberForm memberA = new MemberForm("memberA");
        MemberForm memberB = new MemberForm("memberA");

        memberService.join(memberA);
        memberService.join(memberB);

        fail("중복 회원 가입 시, 예외가 발생해야 함.");
    }

    @Test
    public void 회원정보인증(){
        MemberForm form = new MemberForm("memberA", "1234");
        Long join = memberService.join(form);
        Member findMember = memberRepository.findById(join);

        Member auth = memberService.auth(form);

        assertThat(auth.getPassword()).isEqualTo(findMember.getPassword());
    }

    @Test
    public void 회원정보수정(){
        MemberForm form = new MemberForm("memberA", "1234");
        Long id = memberService.join(form);
        Member findMember = memberRepository.findById(id);

        memberService.modify(findMember.getId(), new MemberForm("memberB", "3456"));
    }
}
