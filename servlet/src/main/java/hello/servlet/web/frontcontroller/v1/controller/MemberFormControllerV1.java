package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp"; //호출할 jsp view
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); //getRequestDispatcher는 컨트롤러에서 뷰로 이동하도록 경로 만들어줌
        dispatcher.forward(request, response); //forward로 서블릿에서 jsp호출 가능
    }
}
