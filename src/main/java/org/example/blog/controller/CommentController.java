package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.CommentService;
import org.example.blog.Service.PostService;
import org.example.blog.Service.UserService;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class CommentController {
    }

