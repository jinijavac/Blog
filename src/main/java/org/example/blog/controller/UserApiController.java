package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/api/user/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/user/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    //스프링 시큐리티 실행 시 모든 페이지가 login으로 리턴
    @PostMapping("/auth/join")
    public ResponseEntity<String> save(@RequestBody User user) {
        System.out.println("UserAPI save 호출");
        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok("회원가입이 완료되었습니다."); // 성공 메시지 반환
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패하였습니다."); // 실패 메시지 반환
        }
    }
}