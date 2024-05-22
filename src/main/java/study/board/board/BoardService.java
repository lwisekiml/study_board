package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import study.board.file.UploadFileRepository;
import study.board.util.FileStore;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final UploadFileRepository uploadFileRepository;

    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    @Transactional
    public Page<BoardDto> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardDto::toDto);
    }

    // validation 할 때 수정
    @Transactional
    public void create(BoardCreateDto boardCreateDto, Model model) throws IOException {

//        boardRepository.save(
//                Board.builder()
//                        .title(boardCreateDto.getTitle())
//                        .content(boardCreateDto.getContent())
//                        .uploadFile(fileStore.storeFile(boardCreateDto.getAttachFile()))
//                        .imageFiles(fileStore.storeFiles(boardCreateDto.getImageFiles()))
//                        .build()
//        );

        boardRepository.save(
                new Board(
                        boardCreateDto.getTitle(),
                        boardCreateDto.getContent(),
                        fileStore.storeFile(boardCreateDto.getAttachFile()),
                        fileStore.storeFiles(boardCreateDto.getImageFiles()))
        );
    }

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
                .attachFile(board.getAttachFile())
                .imageFiles(board.getImageFiles())
                .build();
    }

    @Transactional
    public BoardDto findById(Long boardId) {
        return boardRepository.findById(boardId).map(BoardDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void edit(BoardEditDto boardEditDto) throws IOException {

        Board board = boardRepository.findById(boardEditDto.getId()).orElseThrow(IllegalArgumentException::new);

        board.setTitle(boardEditDto.getTitle());
        board.setContent(boardEditDto.getContent());

        if (!boardEditDto.getAttachFile().isEmpty()) {
            board.setAttachFile(fileStore.storeFile(boardEditDto.getAttachFile()));
        }

        if (!boardEditDto.getImageFiles().isEmpty()) {
            uploadFileRepository.deleteAllInBatch(board.getImageFiles());
            board.setImageFiles(fileStore.storeFiles(boardEditDto.getImageFiles()));
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
