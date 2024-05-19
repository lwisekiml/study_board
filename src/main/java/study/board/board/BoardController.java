package study.board.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import study.board.util.FileStore;
import study.board.util.PaginationService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final PaginationService paginationService;
    private final FileStore fileStore;

    @GetMapping("/")
    public String list(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.list(model, pageable);
    }

    // 글쓰기
    @GetMapping("/board/new")
    public String createForm(@ModelAttribute("boardFormDto") BoardFormDto boardFormDto) {
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@ModelAttribute("boardFormDto") BoardFormDto form, Model model) throws IOException {
        return boardService.create(form, model);
    }

    // 글 조회
    @GetMapping("/board/{boardId}")
    public String board(@PathVariable(name = "boardId") Long boardId, Model model) {
        boardService.board(boardId, model);
        return "/board/board";
    }

    // 글 삭제
    @PostMapping("/board/delete")
    public String delete(@ModelAttribute("boardFormDto") BoardFormDto form) {

        Board board = boardRepository.findById(form.getId()).orElseThrow(IllegalArgumentException::new);
        boardRepository.delete(board);

        return "redirect:/";
    }

    // 글 수정
    @GetMapping("/board/{boardId}/edit")
    public String editForm(@PathVariable(name = "boardId") Long boardId, Model model) {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("boardFormDto",
                new BoardFormDto(board.getId()
                        , board.getTitle()
                        , board.getContent()
                        , board.getViews()
                        , board.getUploadFileName()));

        return "board/editBoardForm";
    }

    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @ModelAttribute(name = "boardFormDto") BoardFormDto boardFormDto,
            @RequestParam(name = "attachModiFile", required = false) MultipartFile attachModiFile
    ) throws IOException {

        boardService.edit(boardFormDto, attachModiFile);
        return "redirect:/board/{boardId}";
    }


    // board에 관련되어 있지만 /board로 시작하는 것이 아니라 다른 곳을 옮기는게 좋을거 같은데
    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("boardId") Long boardId) throws MalformedURLException {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        String storeFileName = board.getStoreFileName();
        String uploadFileName = board.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName = {}", uploadFileName);
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
