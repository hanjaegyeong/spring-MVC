package hello.springmvc.basic.request;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}