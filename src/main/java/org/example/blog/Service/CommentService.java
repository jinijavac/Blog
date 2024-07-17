package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.CommentRepository;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Page<Comment> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pageable);
    }

}
