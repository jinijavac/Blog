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
    @Transactional
    public void updateUser(User updateUser) {
        User persistence = userRepository.findById(updateUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));

        // 이메일 업데이트
        persistence.setEmail(updateUser.getEmail());

        // 비밀번호 업데이트
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            String rawPassword = updateUser.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistence.setPassword(encPassword);
        }

        userRepository.save(persistence); // 이 부분이 빠져있어서 저장이 되지 않을 수 있습니다.
    }
}

