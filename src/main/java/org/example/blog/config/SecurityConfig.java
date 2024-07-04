package org.example.blog.config;

import lombok.RequiredArgsConstructor;
import org.example.blog.security.CustomeDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration //빈 등록
@EnableWebSecurity //필터 활성화 (시큐리티 필터로 등록)
@RequiredArgsConstructor
public class SecurityConfig {

    //시큐리티가 대신 로그인 --> 패스워드 가로채기
    //해당 패스워드가 뭘로 해쉬 되어 회원가입이 되었는지 알아야
    //같은 해쉬로 암호화해서 DB에 있는 해쉬와 비교 가능
    //비밀번호 해쉬

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**", "/", "/js/**", "/image/**", "/css/**", "/api/user/check-username","/api/user/check-email").permitAll()
                        .requestMatchers("/api/board").authenticated()
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
                        .permitAll()


                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

