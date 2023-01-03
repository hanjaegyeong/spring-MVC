package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap); //ModelView 타입으로, 기존과 다르게 req, res 파라미터 선언 안되고 맵으로 그냥 받아버림
}
