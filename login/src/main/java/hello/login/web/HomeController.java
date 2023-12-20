package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }


    //쿠키만 이용
    //@CookieValue로 쿠키값 꺼내기. required = false 하면 로그인 안 한 사용자(쿠키값 없는 사용자)도 들어오기 가능
//    @GetMapping
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

    // 직접 만든 세션 이용
//    @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션관리자에 저장된 회원정보조회
        Member member = (Member)sessionManager.getSession(request);

        // 로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    // 스프링 HttpSession 이용
//    @GetMapping
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false); // false로 해야 세션 없을 때 세션 생성 안함
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 login으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    // @SessionAttribute 이용
    // value 받아오는 과정 따로 하지 않고, 애노테이션에서 바로 세션값 받아옴
//    @GetMapping
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (member == null) {
            return "home";
        }

        // 세션이 유지되면 login으로 이동
        model.addAttribute("member", member);
        return "loginHome";
    }

    // ArgumentResolver 이용
    // 컨트롤러에서 공통으로 사용하는 것들을 애노테이션 하나로 묶을 수 있음 (위의 @SessionAttribute를 직접 만든 @Login을 통해 간소화
    @GetMapping
    public String homeLoginV3ArgumentResolver(@Login Member member, Model model) { // 직접만든 argumentresolver인 @Login 사용

        //세션에 회원 데이터가 없으면 home
        if (member == null) {
            return "home";
        }

        // 세션이 유지되면 login으로 이동
        model.addAttribute("member", member);
        return "loginHome";
    }

}