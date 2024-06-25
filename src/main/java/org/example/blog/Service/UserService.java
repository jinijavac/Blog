package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.RoleRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.domain.Role;
import org.example.blog.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Transactional
    public User registerUser(User user) {
        // 사용자 정보 등록
        Role roleUser = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(roleUser));
        return userRepository.save(user);
    }

    // 유저네임으로 사용자가 이미 존재하는지 확인
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // 이메일로 사용자가 이미 존재하는지 확인
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}