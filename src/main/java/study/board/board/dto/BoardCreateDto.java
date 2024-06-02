package study.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateDto { // 글 쓰기 때 사용

    private String loginId; // createForm() 때 세팅됨

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;

    // 첨부파일
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
