package hello.itemservice.massage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null); // 넣을내용, 인자(프로퍼티에서 {0}으로 표기한), 국가
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    // 해당 메시지 없는 경우
    @Test
    void notFoundMessageCode() {
        Assertions.assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf((NoSuchMessageException.class));
    }

    // 디폴트메시지
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지"); //해당 메시지 못찾으면 디폴트메시지값 반환
    }

    // arg값
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null); //arg는 오브젝트 배열 형태로 넘겨야 함
        assertThat(result).isEqualTo("안녕 Spring");
    }

    //국제화_ko&default
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕"); // 디폴트국가
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕"); // 한국
    }

    //국제화_en
    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null,
                Locale.ENGLISH)).isEqualTo("hello");
    }
}
