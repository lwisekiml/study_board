package study.board.board;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import study.board.util.FileStore;
import study.board.util.PaginationService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

        Page<BoardDto> boardDtos = boardService.findAll(pageable);

        if (boardDtos.isEmpty()) {
            log.info("없는 페이지 입니다.");
            return "list";
        }

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), boardDtos.getTotalPages());

        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("boards", boardDtos); // 프론트에 보낼 때 Dto 인지 명시할 필요가 없을 것으로 보여 boards로 함

        return "list";
    }

    // 글쓰기
    @GetMapping("/board/new")
    public String createForm(@ModelAttribute("boardFormDto") BoardDto boardDto) {
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@ModelAttribute("boardFormDto") BoardDto form, Model model) throws IOException {
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
    public String delete(@ModelAttribute("boardFormDto") BoardDto form) {
        boardService.delete(form);
        return "redirect:/";
    }

    // 글 수정
    @GetMapping("/board/{boardId}/edit")
    public String editForm(@PathVariable(name = "boardId") Long boardId, Model model) {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("boardFormDto",
                new BoardDto(board.getId()
                        , board.getTitle()
                        , board.getContent()
                        , board.getViews()
                        , board.getUploadFileName()));

        return "board/editBoardForm";
    }

    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @ModelAttribute(name = "boardFormDto") BoardDto boardDto,
            @RequestParam(name = "attachModiFile", required = false) MultipartFile attachModiFile
    ) throws IOException {

        boardService.edit(boardDto, attachModiFile);
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
