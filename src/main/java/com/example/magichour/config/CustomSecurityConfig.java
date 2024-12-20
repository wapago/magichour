package com.example.magichour.config;

import com.example.magichour.jwt.JwtFilter;
import com.example.magichour.jwt.TokenProvider;
import com.example.magichour.service.member.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    private final TokenProvider tokenProvider;
    private final LogoutService logoutService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/kmdbcsv").permitAll()
                .requestMatchers("/api/magichour/**").permitAll()
                .requestMatchers("/api/auth/join/**").permitAll()
                .requestMatchers("/api/auth/login/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig -> { logoutConfig
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));
                })

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
