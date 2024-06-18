package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import study.board.board.dto.BoardCreateDto;
import study.board.board.dto.BoardDto;
import study.board.board.dto.BoardEditDto;
import study.board.board.dto.ListBoardDto;
import study.board.file.*;
import study.board.member.Member;
import study.board.member.MemberRepository;
import study.board.util.FileStore;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Board> findSearch(String search) {
        return boardRepository.findByTitleContaining(search);
    }

    @Transactional
    public void create(BoardCreateDto boardCreateDto, String loginId) throws IOException {
        boardRepository.save(
                new Board(
                        loginId,
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
    public BoardEditDto findBoardToBoardEditDto(Long boardId) {
        Board board = this.findBoard(boardId);
        return BoardEditDto.toBoardEditDto(board);
    }

    // post 글수정
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
    public void checkEditAuth(Long boardId, Principal principal) {
        Board board = this.findBoard(boardId);
        String loginId = board.getLoginId();

        if (!loginId.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
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

        board.setAttachFile(fileStore.storeFile(boardEditDto.getMulAttachFile()));
    }

    @Transactional
    public void editImageFiles(Board board, BoardEditDto boardEditDto) throws IOException {
        uploadFilesRepository.deleteAllInBatch(board.getImageFiles()); // 기존 이미지 삭제
        board.setImageFiles(fileStore.storeFiles(boardEditDto.getMulImageFiles())); // 첨부된 이미지 저장
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

    @Transactional
    public void getAttachFileAndImageFiles(BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardEditDto.getId()).orElseThrow(IllegalArgumentException::new);
        boardEditDto.setAttachFile(UploadFileDto.toUploadFileDto(board.getAttachFile()));
        boardEditDto.setImageFiles(board.getImageFiles().stream().map(UploadFilesDto::toUploadFilesDto).collect(Collectors.toList()));
    }

    // comment 수정시 사용
    @Transactional
    public BoardDto findBoardToBoardDto(Long boardId) {
        Board board = this.findBoard(boardId);
        return BoardDto.toBoardDto(board);
    }

    public void checkDeleteAuth(Long boardId, Principal principal) throws Exception {
        Board board = this.findBoard(boardId);
        String loginId = board.getLoginId();

        if (!loginId.equals(principal.getName())) {
            throw new Exception("예외");
        }
    }

    @Transactional
    public void recommend(Board board, Member member) {
        // 추천을 누르고 한 번 더 누르면 추천 취소
        if (board.getRecommend().contains(member)) {
            board.getRecommend().remove(member);
        } else {
            board.getRecommend().add(member);
            boardRepository.save(board);
        }
    }

//    // 본인이 작성한 글 찾기
//    @Transactional
//    public List<BoardDto> getMemberWrite(String loginId) {
//        return boardRepository.findMemberDto(loginId);
//    }
}
