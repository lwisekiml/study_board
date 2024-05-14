package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import study.board.dto.BoardFormDto;
import study.board.entity.Board;
import study.board.repository.BoardRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void edit(BoardFormDto boardFormDto) {
        Board board = boardRepository.findById(boardFormDto.getId()).get();

        board.setTitle(boardFormDto.getTitle());
        board.setContent(boardFormDto.getContent());
    }

    @Transactional
    public void board(Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).get();
        board.plusViews();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent(), board.getViews(), board.getUploadFileName()));
    }

    @Transactional
    public List<BoardFormDto> getMemberWrite(String loginId) {
        return boardRepository.findMemberDto(loginId);
    }
}
