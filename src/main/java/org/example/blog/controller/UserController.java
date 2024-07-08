package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.PostService;
import org.example.blog.Service.UserService;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostService postService;

    @GetMapping("/auth/joinform")
    public String joinform() {
        return "user/joinform";
    }
    @GetMapping("/auth/loginform")
    public String loginform() {
        return "user/loginform";
    }
    @GetMapping("/user/updateform")
    public String updateform(@AuthenticationPrincipal Principal principal){
        return "user/updateform";
    }
    @GetMapping("/user/info")
    public String userBlog(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + username));
        List<Post> userPosts = postService.findByUser(user);
        Collections.reverse(userPosts);
        model.addAttribute("user", user);
        model.addAttribute("userPosts", userPosts);

        // 다른 정보들도 필요하다면 추가할 수 있습니다.
        return "user/info";
    }
}