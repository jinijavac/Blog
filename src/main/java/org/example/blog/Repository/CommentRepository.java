package org.example.blog.Repository;

import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    Page<Comment> findByPostId(Long postId, Pageable pageable);

}
