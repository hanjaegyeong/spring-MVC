package hello.springmvc.basic.response;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

//@RestController = @Controller + @ResponseBody
@Slf4j
@Controller
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException{ //string 넘기기-HttpServletResponse
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);  //string, 상태코드 넘기기-ResponseEntity<String>
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() { // string, 상태코드 넘기기-@ResponseBody
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() { //json, 상태코드 넘기기-ResponseEntity<객체>
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    // 보통 클래스레벨에 @RestController 달고 아래처럼 객체만 return 하는 방식 사용
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() { //json, 상태코드 넘기기-@ResponseStatus, @ResponseBody
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}