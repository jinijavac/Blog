package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

//    @GetMapping("/check-username")
//    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
//        boolean exists = userService.existsByUsername(username);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/check-email")
//    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
//        boolean exists = userService.existsByEmail(email);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/join")
    public ResponseEntity<Integer> save(@RequestBody User user) {
        System.out.println("UserAPI save 호출");
        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok(1); // 성공적으로 저장되었을 경우 1을 반환
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 실패 시 0을 반환
        }
    }
}