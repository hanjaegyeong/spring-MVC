package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// service 코드
@Component
public class FileStore {

    @Value("${file.dir}")

    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //파일 여러개 업로드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile)); //UploadFile 객체 storeFileResult에 add
            }
        }
        return storeFileResult;
    }

    //단일 파일 업로드
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // image.png

        String storeFileName = createStoreFileName(originalFilename); // qwe-qwe-123-qwe.png

        multipartFile.transferTo(new File(getFullPath(storeFileName))); //transferTo(경로): 경로에 파일 저장

        return new UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext; //랜덤 uuid.확장자 형태로 리턴
    }

    private String extractExt(String originalFilename) { // 확장자(png) 추출하는 함수
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}