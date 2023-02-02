package jinwoo.basetown.service;

import jinwoo.basetown.dto.AuthForm;
import jinwoo.basetown.dto.MemberForm;
import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true) //트랜잭션 동작 내 읽기 동작만 수행 -> 플러시 동작을 막아줌
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional //readOnly 해제
    public Long join(@Valid MemberForm form){

        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAddress(form.getAddress());
        member.setAge(form.getAge());
        member.setPosition(form.getPosition());

        validateDuplicateMember(member); //중복 회원 검사
        memberRepository.save(member);

        return member.getId();
    }

    //중복 회원 검사
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());

        if (!findMembers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    //회원 로그인
    public Member signin(String username, String password){
        return memberRepository.findByUsername(username).stream()
                .filter(m -> m.getPassword().equals(password))
                .findAny()
                .orElse(null);
    }

    //회원 정보 수정
    @Transactional
    public void modify(Long id, @Valid MemberForm form){
        Member findMember = memberRepository.findById(id);

        findMember.setName(form.getName());
        findMember.setUsername(form.getUsername());
        findMember.setAge(form.getAge());
        findMember.setAddress(form.getAddress());
        findMember.setPosition(form.getPosition());
    }

    //회원 인증
    public Member auth(@Valid AuthForm form){
        return memberRepository.findByPassword(form.getPassword());
    }
}
