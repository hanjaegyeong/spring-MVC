package hello.upload.controller;

import hello.upload.domain.UploadFile;
import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    //아이템 등록 view 리턴
    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    //아이템 등록 시 파일 저장
    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        //단일파일 저장
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile()); // getAttachFile()로 MultipartFile 객체 받아서 경로에 저장
        //다중이미지파일 저장
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //데이터베이스에 저장 - 이땐 파일 자체를 저장x, 경로만 저장(보통은 절대경로x 상대경로o)
        Item item = new Item();
        item.setItemName(form.getItemName()); //상품명 저장
        item.setAttachFile(attachFile); //파일 저장
        item.setImageFiles(storeImageFiles); //이미지파일들 저장
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId()); //아래 redirect url 파라미터 세팅
        return "redirect:/items/{itemId}";
    }

    //업로드한 상품 확인 가능 view리턴 - 업로드한 상품명, 이미지, 파일 출력
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    //이미지 다운 시 매핑되는 주소
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename)); //파일에 직접 접근해 반환
    }

    //파일 다운 시 매핑되는 주소
    @GetMapping("/attach/{itemId}")  //헤더 세팅 위해 반환형 ResponseEntity<Resource>로
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId); //아이템 접근 권한 있는 사용자만 접근 가능하도록 itemId 받기
        String storeFileName = item.getAttachFile().getStoreFileName(); //저장된 파일 이름
        String uploadFileName = item.getAttachFile().getUploadFileName(); //다운받을 땐 업로드한 파일 이름으로 받아야 함
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName)); //다운받을 객체(body)

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); //인코딩된 파일명
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\""; //contentDisposition 설정

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition) //contentDisposition 헤더 설정해야 다운됨(규약)
                .body(resource); //body에 다운받을 객체 세팅
    }
}