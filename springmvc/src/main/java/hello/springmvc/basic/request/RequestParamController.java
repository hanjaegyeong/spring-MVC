package hello.springmvc.basic.request;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Slf4j
@Controller
public class RequestParamController {
    //쿼리 파라미터 GET 시: localhost:8080/request-param-v1?username=hello&age=20 형태로 요청
    //Form POST 시: localhost:8080/basic/hello-form.html 위치에서 폼에 입력하면 ?username=hello&age=20 입력된 후 action을 통해 /request-param-v1로 이동
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody //RequstMapping으로 인해 String 반환시 ok.jsp라는 view 연결할까봐 @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String mamberName,
            @RequestParam("age") int age) {

        log.info("username={}, age={}", mamberName, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String userName, //받을 파라미터와 이름 같으면 생략 가능
            @RequestParam int age) {

        log.info("username={}, age={}", userName, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
            @RequestParam(required = true, defaultValue = "guest") String userName,
             int age) { //required, defaltValue 지정 가능
        log.info("username={}, age={}", userName, age);
        return "ok";
    }
}