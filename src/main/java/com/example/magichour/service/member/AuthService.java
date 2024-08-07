package com.example.magichour.service.member;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.entity.member.Authority;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.entity.member.UserEntity;
import com.example.magichour.jwt.TokenProvider;
import com.example.magichour.repository.UserRepository;
import com.example.magichour.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserEntity join(JoinRequest joinRequest) {
        String requestEmail = joinRequest.getUserEmail();
        boolean isExistEmail = userRepository.existsByUserEmail(requestEmail);

        if (isExistEmail) {
            throw new RuntimeException("======== 이미 존재하는 이메일입니다 ========");
        }

        Authority authority = Authority.USER;

        UserEntity user = UserEntity.builder()
                .userEmail(joinRequest.getUserEmail())
                .userName(joinRequest.getUserName())
                .userPassword(passwordEncoder.encode(joinRequest.getUserPassword()))
                .authority(authority)
                .activated(true)
                .build();

        userRepository.save(user);

        return user;
    }

    public TokenDto login(LoginRequest loginRequest, Authentication authentication) {
        Optional<UserEntity> userOptional = userRepository.findByUserEmail(loginRequest.getUserEmail());

        if (!userOptional.isPresent()) {
            throw new RuntimeException("======== 존재하지 않는 회원입니다 ========");
        }

        return tokenProvider.createJwt(authentication);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserWithAuthorities(String userEmail) {
        return userRepository.findOneWithAuthoritiesByUserEmail(userEmail);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUserId().flatMap(userRepository::findOneWithAuthoritiesByUserEmail);
    }
}
