package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//Validator 분리. 컨트롤러에서 검증 로직만 다른 클래스로 분리한 것
@Component //autowired 위해 컴포넌트 빈으로 등록
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz); //item == clazz || item == subItem 검증. 즉 isAssignableFrom()은 자식클래스까지 커버해줌
    }
    @Override
    public void validate(Object target, Errors errors) { //Errors는 bindingResult의 부모클래스
        Item item = (Item) target; //타겟은 캐스팅 필요: validate()함수에 Object타입이 이미 박혀있어서
        
        // 원래 검증로직
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000},
                    null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            }
        }
    }
}