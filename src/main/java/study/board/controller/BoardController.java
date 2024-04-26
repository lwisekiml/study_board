package study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    @GetMapping("/")
    public String list() {
        return "list";
    }

    @GetMapping("/board")
    public String board(@RequestParam("id") int id) {
        System.out.println("id = " + id);

        return "board";
    }
}
