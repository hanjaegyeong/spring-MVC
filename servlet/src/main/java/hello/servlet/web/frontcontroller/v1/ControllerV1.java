package hello.servlet.web.frontcontroller.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV1 {

    //서블릿의 service와 이름 빼고 기능 다 동일. 프론트 컨트롤러 서블릿의 service()에서 받은 request, response를 처리***
    //해당 ControllerV1를 implement한 모든 컨트롤러에서 process 구현 필수
    //즉 프론트 컨트롤러에서 ControllerV1 타입&하위컨트롤러 인스턴스로 객체만들어서 controller.process(request, response);로 보내버리면 해당 하위 컨트롤러 로직 처리
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
