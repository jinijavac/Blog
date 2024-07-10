package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.CommentRepository;
import org.example.blog.Repository.PostRepository;
import org.example.blog.Repository.RoleRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.example.blog.domain.Role;
import org.example.blog.domain.User;
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
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없음");
                });

    }

    @Transactional
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        System.out.println("글삭제하기");
        if (post.isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void updatepost(Long id, Post updatePost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없음");
                });
        post.setTitle(updatePost.getTitle());
        post.setContent(updatePost.getContent());

    }

    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    public Post findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new RuntimeException("Post not found")); // 혹은 Optional이 비어있을 때 예외를 던지도록 설정할 수 있습니다.
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
    public void writeReplyComment(Long postId, Long parentCommentId, Comment comment, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + parentCommentId));
        comment.setPost(post);
        comment.setUser(user);
        comment.setParentComment(parentComment);
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}




