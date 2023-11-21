package com.example.magichour.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("---------configure---------");

        return  httpSecurity
                        .httpBasic().disable()
                        .csrf().disable()
                        .cors()

                        .and()
                        .authorizeHttpRequests()    // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/member/join").permitAll()   // 회원가입 api
                        .requestMatchers("/member/login").permitAll()
                        .anyRequest().authenticated()

                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 씀

                        .and()
                        .build();

    }

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
