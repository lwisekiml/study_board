package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.board.dto.BoardCreateDto;
import study.board.board.dto.BoardDto;
import study.board.board.dto.BoardEditDto;
import study.board.board.dto.ListBoardDto;
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

    // validation 할 때 수정
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
    @Transactional
    public BoardDto findBoardPlusViewToBoardDto(Long boardId) {
        Board board = this.findBoard(boardId);
        board.plusViews();
        return BoardDto.toBoardDto(board);
    }

    @Transactional
    public BoardDto findBoardToBoardDto(Long boardId) {
        Board board = this.findBoard(boardId);
        return BoardDto.toBoardDto(board);
    }

    @Transactional
    public void edit(BoardEditDto boardEditDto) throws IOException {

        Board board = this.findBoard(boardEditDto.getId());

        board.setTitle(boardEditDto.getTitle());
        board.setContent(boardEditDto.getContent());

        // 첨부파일
        editAttachFile(board, boardEditDto);
        editImageFiles(board, boardEditDto);
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
    public void delete(BoardDto boardDto) {
        boardRepository.delete(this.findBoard(boardDto.getId()));

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
