package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.PostRepository;
import org.example.blog.Repository.RoleRepository;
import org.example.blog.Repository.UserRepository;
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
    public Post postdetail(Long id){
        return postRepository.findById(id)
                .orElseThrow(()->{
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
}



