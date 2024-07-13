package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Service.LikeService;
import org.example.blog.Service.UserService;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeApiController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/api/board/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetail principal) {
        likeService.likePost(id, principal.getUser());
        return ResponseEntity.ok("게시글에 좋아요를 눌렀습니다.");
    }
    @PostMapping("/api/board/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        likeService.unlikePost(postId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/board/{postId}/isLiked")
    public ResponseEntity<Boolean> isPostLikedByUser(@PathVariable Long postId) {
        boolean isLiked = likeService.isPostLikedByUser(postId);
        return ResponseEntity.ok(isLiked);
    }
}