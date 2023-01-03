package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

// 롬복 라이브러리로 username, age 게터세터 자동생성
@Getter
@Setter
public class HelloData {

    private String username;
    private int age;

    
}
