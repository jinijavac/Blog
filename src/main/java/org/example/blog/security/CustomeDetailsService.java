package org.example.blog.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.UserRepository;
import org.example.blog.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomeDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다" + username);
                });
        return new PrincipalDetail(principal); //시큐리티 세션에 유저 정보 저장
    }
}
