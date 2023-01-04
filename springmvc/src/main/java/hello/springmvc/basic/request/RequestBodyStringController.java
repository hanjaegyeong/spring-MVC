package hello.springmvc.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

//HTTP요청 바디메시지-일반 텍스트 받기
@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBdoy={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException { //InputStream, Writer 파라미터로 바로 받기
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBdoy={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {  //HTTP 메시지 컨버터
        String messageBody = httpEntity.getBody();
        log.info("messageBdoy={}", messageBody);

        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v4")
    public HttpEntity requestBodyStringV3(@RequestBody String messageBody) throws IOException { //애노테이션으로 최적화
        log.info("messageBdoy={}", messageBody);
        return new HttpEntity<>("ok");
    }
}
