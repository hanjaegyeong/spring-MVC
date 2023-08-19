package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//MessageCodesResolver: rejectValue() , reject()가 내부에서 사용하는 MessageCodesResolver 객체는 필드값, 객체명, 필드타입, 오류코드 등을 저장하여 자세한 메시지부터 차례대로 찾아줌
public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        Assertions.assertThat(messageCodes).containsExactly("required.item", "required"); //가장 자세, 덜 자세 두 값 다 가짐!
    }

    @Test
    void messageCodesResolverFiled() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName", //가장 자세 level1
                "required.itemName", //덜 자세 level2
                "required.java.lang.String", //타입 level3 ex) 필수 문자입니다, 숫자입니다 등 띄울 때
                "required" //에러코드 자체 level4
        );
    }
}
