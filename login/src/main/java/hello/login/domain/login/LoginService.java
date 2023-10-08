package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * @return null 이면 로그인 실패
     */
    public Member login(String loginId, String password) {
/*        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId); //id 찾아서
        Member member = findMemberOptional.get();
        if (member.getPassword().equals(password)) { //패스워드 대조
            return member;
        } else {
            return null;
        }*/

        // 위 코드와 동일
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
