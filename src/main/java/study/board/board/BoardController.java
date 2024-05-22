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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

        Page<BoardDto> pageBoardDto = boardService.findAll(pageable);

        if (pageBoardDto.isEmpty()) {
            log.info("없는 페이지 입니다.");
            return "list";
        }

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), pageBoardDto.getTotalPages());

        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("boards", pageBoardDto); // 프론트에 보낼 때 Dto 인지 명시할 필요가 없을 것으로 보여 boards로 함

        return "list";
    }

    // 글쓰기
    @GetMapping("/board/new")
    public String createForm(@ModelAttribute("boardCreateDto") BoardCreateDto boardCreateDto) {
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@Validated @ModelAttribute("boardCreateDto") BoardCreateDto boardCreateDto, BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "/board/createBoardForm";
        }

        boardService.create(boardCreateDto, model);

        return "redirect:/";
    }

    // 글 조회
    @GetMapping("/board/{boardId}")
    public String board(@PathVariable(name = "boardId") Long boardId, Model model) {
        model.addAttribute("boardDto", boardService.board(boardId, model));
        return "/board/board";
    }

    // 글 수정
    @GetMapping("/board/{boardId}/edit")
    public String editForm(@PathVariable(name = "boardId") Long boardId, Model model) {
        model.addAttribute("boardEditDto", boardService.findById(boardId));
        return "board/editBoardForm";
    }

    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @Validated @ModelAttribute("boardEditDto") BoardEditDto boardEditDto,
            BindingResult bindingResult,
            @RequestParam(name = "newAttachFile", required = false) MultipartFile newAttachFile
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "/board/editBoardForm";
        }

        boardService.edit(boardEditDto, newAttachFile);
        return "redirect:/board/{boardId}";
    }

    // 글 삭제
    @PostMapping("/board/delete")
    public String delete(@ModelAttribute("boardDto") BoardDto form) {
        boardService.delete(form);
        return "redirect:/";
    }
    
    // board에 관련되어 있지만 /board로 시작하는 것이 아니라 다른 곳을 옮기는게 좋을거 같은데
    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("boardId") Long boardId) throws MalformedURLException {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        String storeFileName = board.getAttachFile().getStoreFileName();
        String uploadFileName = board.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName = {}", uploadFileName);
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
