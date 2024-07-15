package org.example.blog.Repository;

import org.example.blog.domain.Like;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
    void deleteByUserAndPost(User user, Post post);
}
