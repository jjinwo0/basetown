package jinwoo.basetown.service;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //트랜잭션 동작 내 읽기 동작만 수행 -> 플러시 동작을 막아줌
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional //readOnly 해제
    public Long join(Member member){
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

    @Transactional
    public void update(Long id, String name, int age, String address, String position){
        Member member = memberRepository.findById(id);
        member.setUsername(name);
        member.setAge(age);
        member.setAddress(address);
        member.setPosition(position);
    }
}
