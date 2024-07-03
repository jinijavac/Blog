package org.example.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //빈 등록
@EnableWebSecurity //필터 활성화 (시큐리티 필터로 등록)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    //시큐리티가 대신 로그인 --> 패스워드 가로채기
    //해당 패스워드가 뭘로 해쉬 되어 회원가입이 되었는지 알아야
    //같은 해쉬로 암호화해서 DB에 있는 해쉬와 비교 가능
    //비밀번호 해쉬
    

    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**", "/", "/js/**", "/image/**", "/css/**", "/api/user/check-username","/api/user/check-email").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth/loginform")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/auth/login")
                        //스프링 시큐리티가 해당 주소로 로그인 가로채서 대신 로그인을 해줌


                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")


                )
                .sessionManagement(sessionManagement -> sessionManagement
                                .maximumSessions(1) //동시 접속 허용 개수
                                .maxSessionsPreventsLogin(true) //동시 로그인을 차단 defalt - false (먼저 로그인한 사용자 차단)
                        // true - 애초에 허용개수를 초과하는 사용자는 로그인이 안되도록 차단.
                )

                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}