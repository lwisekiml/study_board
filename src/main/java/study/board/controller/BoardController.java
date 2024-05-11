package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.board.argumentresolver.Login;
import study.board.dto.BoardFormDto;
import study.board.dto.LoginFormDto;
import study.board.entity.Board;
import study.board.repository.BoardRepository;
import study.board.service.BoardService;
import study.board.session.SessionConst;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final LoginController loginController;
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

        // session에 회원 데이터가 없으면 list로 이동

        List<Board> boards = boardRepository.findAll(); // dto로 바꿔서 넘기도록 수정 필요
        model.addAttribute("boards", boards);

        if (loginFormDto == null) {
            return "list";
        }

        // session이 유지되면 로그인으로 이동
        model.addAttribute("loginFormDto", loginFormDto);
        return "loginBoard";
    }

    // 게시글 클릭
    // localhost:8080/board?id=1
    @GetMapping("/board")
    public String board(@RequestParam("id") long id) {
        System.out.println("id = " + id);

        return "post";
    }


    ///////////////////////////////////////////


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
    public String post(@PathVariable(name = "boardId") Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).get();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent()));
        return "/board/board";
    }

    // 글 삭제
    @PostMapping("/board/delete")
    public String delete(
            @ModelAttribute BoardFormDto form
    ) {
        Board board = boardRepository.findById(form.getId()).get();
        boardRepository.delete(board);
        return "redirect:/";
    }

    // 글 수정
    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable(name = "boardId") Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).get();
        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent()));
        return "board/editBoardForm";
    }

    // 상품 수정 폼에서 저장 클릭
    @PostMapping("/{boardId}/edit")
    public String edit(@ModelAttribute BoardFormDto boardFormDto) {

        boardService.edit(boardFormDto);

//        Board post = boardRepository.findById(BoardFormDto.getId()).get();
//
//        // entity 변경 감지로 수정 됨
//        post.setTitle(BoardFormDto.getTitle());
//        post.setContent(BoardFormDto.getContent());

        return "redirect:/board/{boardId}";
    }

}
