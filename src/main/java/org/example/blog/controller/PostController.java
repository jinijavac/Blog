package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.PostRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.PostService;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("latestposts", postService.findAllPostsOrderBycreatedDateDesc());
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.postdetail(id));
        postService.postdetail(id);
        return "board/detail";

    }

    @GetMapping("/board/saveform")
    public String saveForm() {
        return "board/saveform";
    }
    //index 페이지로 모델 정보 들고 이동 (viewResolver)

    @GetMapping("/board/{id}/updateform")
    public String updateform(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.postdetail(id));
        return "board/updateform";
    }

}