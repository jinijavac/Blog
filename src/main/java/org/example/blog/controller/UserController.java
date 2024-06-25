package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/joinform")
    public String join() {
        return "user/joinform"; // 실제 회원가입 폼의 HTML 파일명
    }

    // 회원가입 폼 제출 처리

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        // 유저네임 중복 체크
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디가 이미 사용 중입니다.");
        }

        // 이메일 중복 체크
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 이메일입니다.");
        }

        // 회원가입 처리
        userService.registerUser(user);

        return ResponseEntity.ok("회원가입 성공");
    }
}