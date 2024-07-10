package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.PostService;
import org.example.blog.Service.UserService;
import org.example.blog.domain.Comment;
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
    @PutMapping("/api/board/{id}")
    public ResponseEntity<String> updateId(@PathVariable Long id, @RequestBody Post post){
        postService.updatepost(id, post);
        return ResponseEntity.ok("글 수정이 완료되었습니다.");
    }
    @PostMapping("/api/board/{id}/comment")
    public ResponseEntity<String> writeComment(@PathVariable("id") Long postId, @RequestBody Comment comment, @AuthenticationPrincipal PrincipalDetail principal) {
        postService.writeComment(postId, comment, principal.getUser());
        return ResponseEntity.ok("댓글 달기가 완료되었습니다.");
    }

    @PostMapping("/api/board/{postId}/comment/{parentCommentId}/reply")
    public ResponseEntity<String> writeReplyComment(@PathVariable("postId") Long postId, @PathVariable("parentCommentId") Long parentCommentId, @RequestBody Comment comment, @AuthenticationPrincipal PrincipalDetail principal) {
        postService.writeReplyComment(postId, parentCommentId, comment, principal.getUser());
        return ResponseEntity.ok("대댓글 달기가 완료되었습니다.");
    }
    @DeleteMapping("/api/board/{postId}/comment/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable Long commentId){
        postService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제가 완료되었습니다.");
    }
}