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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    @Transactional
    public Page<BoardDto> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardDto::toDto);
    }

    @Transactional
    public String create(BoardDto form, Model model) throws IOException {

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
        model.addAttribute("boardFormDto", new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getViews(), board.getUploadFileName()));
    }

    @Transactional
    public void edit(BoardDto boardDto, MultipartFile attachModiFile) throws IOException {

        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(IllegalArgumentException::new);

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        if (!attachModiFile.isEmpty()) {
            boardDto.setAttachFile(attachModiFile);
            boardDto = fileStore.storeFile(boardDto);

            board.setUploadFileName(boardDto.getUploadFileName());
            board.setStoreFileName(boardDto.getStoreFileName());
        }
    }

    @Transactional
    public void delete(BoardDto form) {
        Board board = boardRepository.findById(form.getId()).orElseThrow(IllegalArgumentException::new);
        boardRepository.delete(board);

    }

//    // 본인이 작성한 글 찾기
//    @Transactional
//    public List<BoardDto> getMemberWrite(String loginId) {
//        return boardRepository.findMemberDto(loginId);
//    }
}
