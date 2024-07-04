package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.PostService;
import org.example.blog.Service.UserService;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@Slf4j
@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @PostMapping("/api/board")
    public ResponseEntity<String> save(@RequestBody Post post, @AuthenticationPrincipal PrincipalDetail principal) {
        postService.savePost(post, principal.getUser());
        return ResponseEntity.ok("글 쓰기가 완료되었습니다.");
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");

    }
}