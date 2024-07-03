package org.example.blog.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.RoleRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.domain.Role;
import org.example.blog.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;


    @Transactional
    public User saveUser(User user) {
        try {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            user.setRoles(Collections.singleton(roleUser));
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println("Saving user: " + user);
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사용자 등록 중 오류가 발생했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

