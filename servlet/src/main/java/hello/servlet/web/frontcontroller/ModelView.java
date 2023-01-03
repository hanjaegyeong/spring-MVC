package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {
    private String viewName; //뷰의 논리적 이름
    private Map<String, Object> model = new HashMap<>();//request, response 사용 안하므로 해당 파라미터 정보는 Map으로 받아서 그냥 넘기도록

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
