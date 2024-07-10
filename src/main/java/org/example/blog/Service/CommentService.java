package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.CommentRepository;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }


}
