package org.example.blog.Repository;

import jakarta.transaction.Transactional;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
    List<Post> findByUser(User user);
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
    List<Post> findByTitleContaining(String title);
    List<Post> findByContentContaining(String content);
    List<Post> findByUserUsernameContaining(String username);
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    void incrementViews(Long postId);
    @Query("SELECT p FROM Post p ORDER BY p.viewCount DESC")
    List<Post> findTopTrendingPosts(Pageable pageable);

}