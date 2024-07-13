package org.example.blog.Service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.LikeRepository;
import org.example.blog.Repository.PostRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.domain.Like;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        likeRepository.deleteByUserAndPost(user, post);
    }

    @Transactional
    public boolean isPostLikedByUser(Long postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return likeRepository.findByUserAndPost(user, post).isPresent();
    }
}