package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //롬복기능. final키워드 붙은 필드 생성자 자동 생성해서 주입까지 (@Autowired)
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) { //전체 상품 목록
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items); //모델에 attribute 담으면 view에서 지정한 이름으로 변수 사용 가능!
        return "basic/items";
    }

    @GetMapping("/{itemId}") //특정 상품 상세
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add") //상품 등록 GET요청(URI로 view접속시)
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")//상품 등록. 버튼으로(Form) 응답 오는 경우
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 더 쉬운 버전
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) { //@ModelAttribute사용해 위 코드와 동일한 역할
        itemRepository.save(item);
        //model.addAttribute("item", item); //@ModelAttribute가 item을 모델에 자동 추가, 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")    //"item" 모델name 지정 안해주면 자동으로 받는 어트리뷰트 첫글자 소문자화해서 name지정
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV4(Item item) {    //애노테이션 생략해도 Sring 제외 타입은 모두 Model로 받음
        itemRepository.save(item);
        return "basic/item";
    }

    // 상품 수정 폼 GET
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    // 상품 수정 처리 POST
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        //컨트롤러에 매핑된 path 변수를 리다이렉트에서도 사용 가능
        return "redirect:/basic/items/{itemId}"; // 리다이렉트!!*** 상품 상세 화면으로 이동
    }

    //테스트용 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}