package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    
    //@CookieValue로 쿠키값 꺼내기. required = false 하면 로그인 안 한 사용자(쿠키값 없는 사용자)도 들어오기 가능
    @GetMapping
    public String homeLogin(@CookieValue(name =  "memberId", required = false) Long memberId, Model model) {

        if (memberId == null) { //로그인 x 사용자
            return "home";
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";

    }
}