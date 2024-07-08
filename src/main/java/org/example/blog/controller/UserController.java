package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

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
}