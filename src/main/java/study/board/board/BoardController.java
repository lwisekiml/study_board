package study.board.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import study.board.board.dto.BoardCreateDto;
import study.board.board.dto.BoardDto;
import study.board.board.dto.BoardEditDto;
import study.board.board.dto.ListBoardDto;
import study.board.member.Member;
import study.board.member.MemberService;
import study.board.oauth2.UserEntity;
import study.board.oauth2.UserRepository;
import study.board.util.FileStore;
import study.board.util.PaginationService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final PaginationService paginationService;
    private final FileStore fileStore;

    private final UserRepository userRepository;
    private final MemberService memberService;

    @GetMapping("/")
    public String list(
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        log.error("error from log");
        log.warn( "warn from log");
        log.info( "info from log");
        log.debug("debug from log");
        log.trace("trace from log");

        Page<ListBoardDto> listBoardDtos = null;

        if (search.isEmpty()) {
            listBoardDtos = boardService.findAll(pageable);
        }
        else {
            List<Board> boardList = boardService.findSearch(search);
            List<ListBoardDto> list = new java.util.ArrayList<>(boardList.stream().map(ListBoardDto::toListBoardDto).toList());
            // PageImpl<>() 은 정렬기능이 없어서 미리 sort
            // 참고 : https://stackoverflow.com/questions/56946999/can-we-sort-listt-from-spring-boot-pageable-or-sort-page-from-pageimpl
            Collections.sort(list);
            PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 10);
            int start = (int) pageRequest.getOffset();
            int end = Math.min(start + pageRequest.getPageSize(), list.size());
            listBoardDtos = new PageImpl<>(list.subList(start, end), pageRequest, list.size());
        }

        if (listBoardDtos.isEmpty()) {
            log.info("없는 페이지 입니다.");
            return "list";
        }

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), listBoardDtos.getTotalPages());

        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("listBoardDtos", listBoardDtos);
        model.addAttribute("search", search); // 검색어를 화면에 유지하기 위함

        return "list";
    }

    // 글쓰기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/new")
    public String createForm(
            @ModelAttribute("boardCreateDto") BoardCreateDto boardCreateDto
            ,Principal principal
            ,Model model
    ) {
        // kakao로그인시 oauth2_authorized_client 테이블에 principal_name 값이 나온다.
        boardCreateDto.setLoginId(principal.getName());
        model.addAttribute("boardCreateDto", boardCreateDto);
        return "board/createBoardForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/new")
    public String create(
            @Validated @ModelAttribute("boardCreateDto") BoardCreateDto boardCreateDto
            , BindingResult bindingResult
            , Principal principal
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "board/createBoardForm";
        }

        Member member = boardService.findMember(principal.getName());
        boardService.create(boardCreateDto, member.getLoginId());

        return "redirect:/";
    }

    // 글 조회
    @GetMapping("/board/{boardId}")
    public String board(
            @PathVariable(name = "boardId") Long boardId,
            Model model
    ) {
        model.addAttribute("boardDto", boardService.findBoardPlusViewToBoardDto(boardId));
        return "board/board";
    }

    // 글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/{boardId}/edit")
    public String editForm(
            @PathVariable(name = "boardId") Long boardId,
            Model model,
            Principal principal
    ) {
        boardService.checkEditAuth(boardId, principal);
        model.addAttribute("boardEditDto", boardService.findBoardToBoardEditDto(boardId));
        return "board/editBoardForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/{boardId}/edit")
    public String edit(
            @Validated @ModelAttribute("boardEditDto") BoardEditDto boardEditDto,
            BindingResult bindingResult,
            Principal principal
    ) throws IOException {

        // 에러 발생 또는 exception 발생시 프론트에 기존 파일들이 보이도록 하기 위함
        boardService.getAttachFileAndImageFiles(boardEditDto);

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "board/editBoardForm";
        }

        try {
            boardService.checkEditAuth(boardEditDto.getId(), principal);
        } catch (Exception e) {
            bindingResult.reject("EditFailed", "수정 권한이 없습니다.");
            log.info("edit errors = {}", bindingResult);
            return "board/editBoardForm";
        }

        boardService.edit(boardEditDto);
        return "redirect:/board/{boardId}";
    }

    // 글 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board/delete/{boardId}")
    public String delete(
            @PathVariable(name = "boardId") Long boardId,
            @ModelAttribute("boardDto") BoardDto boardDto,
            BindingResult bindingResult,
            Principal principal
    ) {
        try {
            boardService.checkDeleteAuth(boardId, principal);
        } catch (Exception e) {
            bindingResult.reject("DeleteFailed", "삭제 권한이 없습니다.");
            log.error("delete errors = {}", bindingResult);
            return "board/board";
        }

        boardService.delete(boardId);
        return "redirect:/";
    }
    
    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(
            @PathVariable("boardId") Long boardId
    ) throws MalformedURLException {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        String storeFileName = board.getAttachFile().getStoreFileName();
        String uploadFileName = board.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName = {}", uploadFileName);
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(
            @PathVariable("filename") String filename
    ) throws MalformedURLException {

        String s = "file:" + fileStore.getFullPath(filename);
        return new UrlResource(s);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/recommend/{boardId}")
    public String boardRecommend(
            Principal principal,
            @PathVariable("boardId") String id
    ) {
        Long boardId = Long.valueOf(id);
        // 밑에 둘 순서가 바뀌면 member값이 null로 나온다.
        Member member = memberService.findMember(principal.getName());
        Board board = boardService.findBoard(boardId);
        boardService.recommend(board, member);

        return String.format("redirect:/board/%d", boardId);
    }
}
