package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/joinform")
    public String join() {
        return "joinform"; // 실제 회원가입 폼의 HTML 파일명
    }
}
