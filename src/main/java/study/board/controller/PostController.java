//package study.board.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import study.board.dto.BoardFormDto;
//import study.board.dto.LoginFormDto;
//import study.board.entity.Board;
//import study.board.repository.BoardRepository;
//import study.board.service.BoardService;
//
//@Controller
//@RequiredArgsConstructor
//public class PostController {
//
//    private final BoardRepository boardRepository;
//
//    private final BoardService boardService;
//
//    // 글쓰기
//    @GetMapping("/board/new")
//    public String createForm(
//            HttpServletRequest request,
//            Model model
//    ) {
//        LoginFormDto loginFormDto = (LoginFormDto) request.getSession().getAttribute("loginMember");
//        model.addAttribute("loginFormDto", loginFormDto);
//        return "board/createBoardForm";
//    }
//
//    @PostMapping("/board/new")
//    public String create(
//            @ModelAttribute("loginFormDto") LoginFormDto loginFormDto,
//            @ModelAttribute("boardFormDto") BoardFormDto form,
//            HttpServletRequest request
//    ) {
//        form.setLoginId(loginFormDto.getLoginId());
//        boardRepository.save(new Board(form.getLoginId(), form.getTitle(), form.getContent()));
//
//        return "redirect:/";
//    }
//
//    // 글 조회
//    @GetMapping("board/{boardId}")
//    public String post(@PathVariable(name = "boardId") Long boardId, Model model) {
//        Board board = boardRepository.findById(boardId).get();
//        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent()));
//        return "list";
//    }
//
//    // 글 삭제
//    @PostMapping("/board/delete")
//    public String delete(
//            @ModelAttribute BoardFormDto form
//    ) {
//        Board board = boardRepository.findById(form.getId()).get();
//        boardRepository.delete(board);
//        return "redirect:/";
//    }
//
//    // 글 수정
//    @GetMapping("/{boardId}/edit")
//    public String editForm(@PathVariable(name = "boardId") Long boardId, Model model) {
//        Board board = boardRepository.findById(boardId).get();
//        model.addAttribute("boardFormDto", new BoardFormDto(board.getId(), board.getLoginId(), board.getTitle(), board.getContent()));
//        return "board/editBoardForm";
//    }
//
//    // 상품 수정 폼에서 저장 클릭
//    @PostMapping("/{boardId}/edit")
//    public String edit(@ModelAttribute BoardFormDto boardFormDto) {
//
//        boardService.edit(boardFormDto);
//
////        Board post = boardRepository.findById(BoardFormDto.getId()).get();
////
////        // entity 변경 감지로 수정 됨
////        post.setTitle(BoardFormDto.getTitle());
////        post.setContent(BoardFormDto.getContent());
//
//        return "redirect:/board/board/{boardId}";
//    }
//}
