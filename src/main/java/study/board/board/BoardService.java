package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import study.board.util.FileStore;
import study.board.util.PaginationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PaginationService paginationService;
    private final FileStore fileStore;

    @Transactional
    public String list(Model model, Pageable pageable) {

        Page<Board> page = boardRepository.findAll(pageable); // 단순 조회(?)인데 BoardService로 옮기는 게 좋을까?
        if (page.isEmpty()) {
            log.info("없는 페이지 입니다.");
            return "list";
        }

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), page.getTotalPages());

        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("boards", page);

        return "list";
    }

    @Transactional
    public String create(BoardFormDto form, Model model) throws IOException {

        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(form.getTitle())) {
            errors.put("titleError", "제목은 필수 입니다.");
        }

        if (!StringUtils.hasText(form.getContent())) {
            errors.put("contentError", "내용은 필수 입니다.");
        }

        if (!errors.isEmpty()) {
            log.info("errors = {}", errors);
            model.addAttribute("errors", errors);
            return "/board/createBoardForm";
        }

        form = fileStore.storeFile(form); //새로운 변수로 해야할거 같은데
        boardRepository.save(new Board(form.getTitle(), form.getContent(), form.getUploadFileName(), form.getStoreFileName()));

        return "redirect:/";
    }

    @Transactional
    public void board(Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.plusViews();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getTitle(), board.getContent(), board.getViews(), board.getUploadFileName()));
    }

    @Transactional
    public void edit(BoardFormDto boardFormDto, MultipartFile attachModiFile) throws IOException {

        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(IllegalArgumentException::new);

        board.setTitle(boardFormDto.getTitle());
        board.setContent(boardFormDto.getContent());

        if (!attachModiFile.isEmpty()) {
            boardFormDto.setAttachFile(attachModiFile);
            boardFormDto = fileStore.storeFile(boardFormDto);

            board.setUploadFileName(boardFormDto.getUploadFileName());
            board.setStoreFileName(boardFormDto.getStoreFileName());
        }
    }

//    // 본인이 작성한 글 찾기
//    @Transactional
//    public List<BoardFormDto> getMemberWrite(String loginId) {
//        return boardRepository.findMemberDto(loginId);
//    }
}
