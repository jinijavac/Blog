package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.*;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.example.blog.domain.Role;
import org.example.blog.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void savePost(Post post, User user) {
        post.setViewCount(0);
        post.setUser(user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPostsOrderBycreatedDateDesc() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }


    @Transactional(readOnly = true)
    public Post postdetail(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없음"));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        postRepository.delete(post);
    }


    @Transactional
    public void updatepost(Long id, Post updatePost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없음"));
        post.setTitle(updatePost.getTitle());
        post.setContent(updatePost.getContent());
    }

    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Transactional
    public void writeComment(Long postId, Comment requestComment, User user){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("게시글 아이디를 찾을 수 없음");
                });
        requestComment.setUser(user);
        requestComment.setPost(post);

        commentRepository.save(requestComment);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    public List<Post> searchPosts(String query) {
        return postRepository.findByTitleContainingOrContentContaining(query, query);
    }
    public List<Post> searchByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

    public List<Post> searchByContent(String content) {
        return postRepository.findByContentContaining(content);
    }
    public List<Post> searchByUserUsername(String username) {
        return postRepository.findByUserUsernameContaining(username);
    }
    public void incrementViews(Long postId) {
        postRepository.incrementViews(postId);
    }
    public List<Post> getTopTrendingPosts() {
        Pageable pageable = PageRequest.of(0, 50); // 페이지 크기 50으로 설정
        return postRepository.findTopTrendingPosts(pageable);
    }

    public long getLikeCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        return likeRepository.countByPost(post);
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }
}




