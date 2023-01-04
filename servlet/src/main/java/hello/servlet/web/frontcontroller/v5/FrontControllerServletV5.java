package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//인터페이스타입 특정 ControllerV3에서 Object타입 handler로!
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>(); //이전엔 Map<String, ControllerV4>였지만 이제 인터페이스 대신 Object
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();//어댑터들 담을 리스트

    public FrontControllerServletV5(){
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3()); //Object이기 때문에 어떠한 자바객체든 담길 수 있음
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        
        //V4 추가: 확장했는데도 추가한 코드들 몇 개 없음
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //해당 request 넣으면 MemberFormControllerV4 반환 (위 handlerMappingMap에서 매핑된 key에 맞는 value)
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //핸들러 어댑터 받아와서(adapter = ControllerV4HandlerAdapter)
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //handle 핸들러 -> ControllerV4로 변환!!, 해당 컨트롤러의 process 호출: mv반환
        ModelView mv = adapter.handle(request, response, handler);

        //뷰리졸버 호출
        String viewName = mv.getViewName();//view 논리이름만 있음
        MyView view = viewResolver(viewName); //view 실제 위치와 합체매서드 (아래 정의함)

        //뷰 렌더링
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) { // 핸들러 받아서
        for (MyHandlerAdapter adapter : handlerAdapters) { // 어댑터 목록 돌리기
            if (adapter.supports(handler)){ // 특정 어댑터가 해당 핸들러 지원한다면(boolean)
                return adapter; //해당 어댑터 반환: ControllerV3HandlerAdapter
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다." + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private static MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");//view 실제 위치와 합체
        return view;
    }
}
