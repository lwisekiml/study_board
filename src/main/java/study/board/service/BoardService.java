package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import study.board.dto.BoardFormDto;
import study.board.entity.Board;
import study.board.repository.BoardRepository;
import study.board.repository.FileStore;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileStore fileStore;

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

    @Transactional
    public void board(Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.plusViews();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent(), board.getViews(), board.getUploadFileName()));
    }

    @Transactional
    public List<BoardFormDto> getMemberWrite(String loginId) {
        return boardRepository.findMemberDto(loginId);
    }
}
