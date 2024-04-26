package study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    // http://localhost:8080/members/add
    @GetMapping("/add")
    public String addForm() {
        return "members/addMemberForm";
    }

    // http://localhost:8080/members/add
    @PostMapping("/add")
    public String save(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("password = " + password);

        return "redirect:/members/welcome";
    }

    // http://localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
