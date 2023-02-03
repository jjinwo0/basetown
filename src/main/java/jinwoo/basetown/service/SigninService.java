package jinwoo.basetown.service;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //트랜잭션 동작 내 읽기 동작만 수행 -> 플러시 동작을 막아줌
@RequiredArgsConstructor
public class SigninService {

    private final MemberRepository memberRepository;

    //회원 로그인
    public Member signin(String username, String password){
        return memberRepository.findByUsername(username).stream()
                .filter(m -> m.getPassword().equals(password))
                .findAny()
                .orElse(null);
    }

}
