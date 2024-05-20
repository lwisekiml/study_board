package study.board.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import study.board.board.BoardDto;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    // 경로
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public BoardDto storeFile(BoardDto form) throws IOException {
        MultipartFile multipartFile = form.getAttachFile();
        if (multipartFile.isEmpty()) {
            return form;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        form.setUploadFileName(originalFilename);
        form.setStoreFileName(storeFileName);
        return form;
    }

    // 새로운 파일 이름 생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename); // 확장자 추출
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
