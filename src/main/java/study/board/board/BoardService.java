package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import study.board.file.UploadFile;
import study.board.file.UploadFileRepository;
import study.board.file.UploadFilesRepository;
import study.board.util.FileStore;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final UploadFileRepository uploadFileRepository;
    private final UploadFilesRepository uploadFilesRepository;

    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    @Transactional
    public Page<BoardDto> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardDto::toDto);
    }

    // validation 할 때 수정
    @Transactional
    public void create(BoardCreateDto boardCreateDto, Model model) throws IOException {
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
    public BoardDto board(Long boardId) {
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
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

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
    public void edit(BoardEditDto boardEditDto) throws IOException {

        Board board = boardRepository.findById(boardEditDto.getId()).orElseThrow(IllegalArgumentException::new);

        board.setTitle(boardEditDto.getTitle());
        board.setContent(boardEditDto.getContent());

        // 첨부파일
        editAttachFile(board, boardEditDto);
        editImageFiles(board, boardEditDto);


//        UploadFile boardAttachFile = board.getAttachFile();
//
//        if (boardAttachFile != null) { // 기존 게시문에 첨부파일 있으면
//            board.setAttachFile(null);
//            uploadFileRepository.delete(boardAttachFile);
//            // jpa delete 안되는 문제 참고 : https://carpet-part1.tistory.com/711
//            uploadFileRepository.flush();
//        }
//
//        board.setAttachFile(fileStore.storeFile(boardEditDto.getAttachFile()));
//
//        // 첨부된 이미지들
//        uploadFilesRepository.deleteAllInBatch(board.getImageFiles()); // 기존 이미지 삭제
//        board.setImageFiles(fileStore.storeFiles(boardEditDto.getImageFiles())); // 첨부된 이미지 저장
    }

    @Transactional
    public void editAttachFile(Board board, BoardEditDto boardEditDto) throws IOException {
        UploadFile boardAttachFile = board.getAttachFile();

        if (boardAttachFile != null) { // 기존 게시문에 첨부파일 있으면
            board.setAttachFile(null);
            uploadFileRepository.delete(boardAttachFile);
            // jpa delete 안되는 문제 참고 : https://carpet-part1.tistory.com/711
            uploadFileRepository.flush();
        }

        board.setAttachFile(fileStore.storeFile(boardEditDto.getAttachFile()));
    }

    @Transactional
    public void editImageFiles(Board board, BoardEditDto boardEditDto) throws IOException {
        uploadFilesRepository.deleteAllInBatch(board.getImageFiles()); // 기존 이미지 삭제
        board.setImageFiles(fileStore.storeFiles(boardEditDto.getImageFiles())); // 첨부된 이미지 저장
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
