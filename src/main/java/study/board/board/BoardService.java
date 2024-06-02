package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.board.dto.*;
import study.board.file.UploadFile;
import study.board.file.UploadFileRepository;
import study.board.file.UploadFilesRepository;
import study.board.member.Member;
import study.board.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public Page<ListBoardDto> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(ListBoardDto::toListBoardDto);
    }

    @Transactional
    public void create(BoardCreateDto boardCreateDto, Member member) throws IOException {
        boardRepository.save(
                new Board(
                        member,
                        boardCreateDto.getTitle(),
                        boardCreateDto.getContent(),
                        fileStore.storeFile(boardCreateDto.getAttachFile()),
                        fileStore.storeFiles(boardCreateDto.getImageFiles()))
        );
    }

    // 깔끔하게 Dto로 넘기고 싶지만 plusViews를 해야 하므로 아래와 같이 함
    // 글 조회
    @Transactional
    public BoardDto findBoardPlusViewToBoardDto(Long boardId) {
        Board board = this.findBoard(boardId);
        board.plusViews();
        return BoardDto.toBoardDto(board);
    }

    // get 글수정
    @Transactional
    public BoardGetEditDto findBoardToBoardGetEditDto(Long boardId) {
        Board board = this.findBoard(boardId);
        return BoardGetEditDto.toBoardGetEditDto(board);
    }

    // post 글수정
    @Transactional
    public void edit(BoardPostEditDto boardPostEditDto) throws IOException {

        Board board = this.findBoard(boardPostEditDto.getId());

        board.setTitle(boardPostEditDto.getTitle());
        board.setContent(boardPostEditDto.getContent());

        // 첨부파일
        editAttachFile(board, boardPostEditDto);
        editImageFiles(board, boardPostEditDto);
    }

    @Transactional
    public void editAttachFile(Board board, BoardPostEditDto boardPostEditDto) throws IOException {
        UploadFile boardAttachFile = board.getAttachFile();

        if (boardAttachFile != null) { // 기존 게시문에 첨부파일 있으면
            board.setAttachFile(null);
            uploadFileRepository.delete(boardAttachFile);
            // jpa delete 안되는 문제 참고 : https://carpet-part1.tistory.com/711
            uploadFileRepository.flush();
        }

        board.setAttachFile(fileStore.storeFile(boardPostEditDto.getAttachFile()));
    }

    @Transactional
    public void editImageFiles(Board board, BoardPostEditDto boardPostEditDto) throws IOException {
        uploadFilesRepository.deleteAllInBatch(board.getImageFiles()); // 기존 이미지 삭제
        board.setImageFiles(fileStore.storeFiles(boardPostEditDto.getImageFiles())); // 첨부된 이미지 저장
    }

    @Transactional
    public void delete(Long boardId) {
        boardRepository.delete(this.findBoard(boardId));
    }

    @Transactional
    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Member findMember(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(IllegalArgumentException::new);
    }

//    // 본인이 작성한 글 찾기
//    @Transactional
//    public List<BoardDto> getMemberWrite(String loginId) {
//        return boardRepository.findMemberDto(loginId);
//    }
}
