package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/joinform")
    public String joinform() {
        return "user/joinform";
    }
    @GetMapping("/loginform")
    public String loginform() {
        return "user/loginform";

    }
}