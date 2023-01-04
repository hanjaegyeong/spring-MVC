package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j //로그찍기용
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    //스프링은 거의 모든 헤더 정보를 받을 수 있도록 다 만들어놓음
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,        //언어정보
                          @RequestHeader MultiValueMap<String, String> headerMap, //모든 헤더. 하나의 키에 여러 value받으려고 mutivaluemap
                          @RequestHeader("host") String host,       //특정 헤더. 호스트는 localhost:8080 리턴
                          @CookieValue(value = "myCookie", required = false)
                          String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
