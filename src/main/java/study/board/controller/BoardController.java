package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.argumentresolver.Login;
import study.board.dto.BoardFormDto;
import study.board.dto.LoginFormDto;
import study.board.entity.Board;
import study.board.repository.BoardRepository;
import study.board.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String list(
            @Login LoginFormDto loginFormDto,
            Model model,
            HttpServletRequest request
    ) {
        // 접속시 로그인 상태로 하기 위함(나중에 삭제 필요)
//        loginController.login(new LoginFormDto(null, "test", "1234"), "/", request);
//        HttpSession session = request.getSession();
//        loginFormDto = (LoginFormDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Board> boards = boardRepository.findAll(); // dto로 바꿔서 넘기도록 수정 필요
        model.addAttribute("boards", boards);
        model.addAttribute("loginFormDto", loginFormDto);

        return "list";
    }

    // 글쓰기
    @GetMapping("/board/new")
    public String createForm(
            HttpServletRequest request,
            Model model
    ) {
        LoginFormDto loginFormDto = (LoginFormDto) request.getSession().getAttribute("loginMember");
        model.addAttribute("loginFormDto", loginFormDto);

        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(
//            @ModelAttribute("loginFormDto") LoginFormDto loginFormDto, // createBoardForm에서 loginId값을 보내면 form에도 loginId값이 설정 되어 주석처리 함
            @ModelAttribute("boardFormDto") BoardFormDto form,
            HttpServletRequest request
    ) {
        boardRepository.save(new Board(form.getLoginId(), form.getTitle(), form.getContent()));

        return "redirect:/";
    }

    // 글 조회
    @GetMapping("/board/{boardId}")
    public String board(
            @PathVariable(name = "boardId") Long boardId, Model model
    ) {
        boardService.board(boardId, model);
        return "/board/board";
    }

    // 글 삭제
    @PostMapping("/board/delete")
    public String delete(
            @ModelAttribute("boardFormDto") BoardFormDto form
    ) {
        Board board = boardRepository.findById(form.getId()).get();
        boardRepository.delete(board);

        return "redirect:/";
    }

    // 글 수정
    @GetMapping("/board/{boardId}/edit")
    public String editForm(
            @PathVariable(name = "boardId") Long boardId, Model model
    ) {
        Board board = boardRepository.findById(boardId).get();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent(), board.getViews()));

        return "board/editBoardForm";
    }

    // 상품 수정 폼에서 저장 클릭
    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @ModelAttribute("boardFormDto") BoardFormDto boardFormDto
    ) {

        boardService.edit(boardFormDto);

        return "redirect:/board/{boardId}";
    }

}
