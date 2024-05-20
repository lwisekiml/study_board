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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
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

    private final BoardValidator boardValidator;

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
    public String createForm(@ModelAttribute("boardDto") BoardDto boardDto) {
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@ModelAttribute("boardDto") BoardDto form, BindingResult bindingResult, Model model) throws IOException {

        boardValidator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "/board/createBoardForm";
        }

        form = fileStore.storeFile(form); //새로운 변수로 해야할거 같은데
        boardRepository.save(
                Board.builder()
                        .title(form.getTitle())
                        .content(form.getContent())
                        .uploadFileName(form.getUploadFileName())
                        .storeFileName(form.getStoreFileName())
                        .build());

        return "redirect:/";
//        return boardService.create(form, model);
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
        model.addAttribute("boardDto", boardService.findById(boardId));
        return "board/editBoardForm";
    }

    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @ModelAttribute(name = "boardDto") BoardDto boardDto,
            @RequestParam(name = "newAttachFile", required = false) MultipartFile newAttachFile
    ) throws IOException {

        boardService.edit(boardDto, newAttachFile);
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
