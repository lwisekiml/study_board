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

    // validation 할 때 수정
//    @Transactional
//    public String create(BoardDto form, Model model) throws IOException {
//
//        Map<String, String> errors = new HashMap<>();
//
//        if (!StringUtils.hasText(form.getTitle())) {
//            errors.put("titleError", "제목은 필수 입니다.");
//        }
//
//        if (!StringUtils.hasText(form.getContent())) {
//            errors.put("contentError", "내용은 필수 입니다.");
//        }
//
//        if (!errors.isEmpty()) {
//            log.info("errors = {}", errors);
//            model.addAttribute("errors", errors);
//            return "/board/createBoardForm";
//        }
//
//        form = fileStore.storeFile(form); //새로운 변수로 해야할거 같은데
//        boardRepository.save(
//                Board.builder()
//                        .title(form.getTitle())
//                        .content(form.getContent())
//                        .uploadFileName(form.getUploadFileName())
//                        .storeFileName(form.getStoreFileName())
//                        .build());
//
//        return "redirect:/";
//    }

    // 깔끔하게 Dto로 넘기고 싶지만 plusViews를 해야 하므로 아래와 같이 함
    @Transactional
    public BoardDto board(Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.plusViews();

        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .views(board.getViews())
                .uploadFileName(board.getUploadFileName())
                .build();
    }

    @Transactional
    public BoardDto findById(Long boardId) {
        return boardRepository.findById(boardId).map(BoardDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void edit(BoardDto boardDto, MultipartFile newAttachFile) throws IOException {

        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(IllegalArgumentException::new);

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        if (!newAttachFile.isEmpty()) {
            boardDto.setAttachFile(newAttachFile);
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
