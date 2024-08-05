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
        String requestId = joinRequest.getUserId();
        boolean isExistId = userRepository.existsByUserId(requestId);

        if (isExistId) {
            throw new RuntimeException("======== 이미 존재하는 아이디입니다 ========");
        }

        Authority authority = Authority.USER;

        UserEntity member = UserEntity.builder()
                .userEmail(joinRequest.getUserId())
                .userName(joinRequest.getUserName())
                .userPassword(passwordEncoder.encode(joinRequest.getUserPassword()))
                .authority(authority)
                .activated(true)
                .build();

        userRepository.save(member);

        return member;
    }

    public TokenDto login(LoginRequest loginRequest, Authentication authentication) {
        Optional<UserEntity> memberOptional = userRepository.findByUserId(loginRequest.getUserEmail());

        if (!memberOptional.isPresent()) {
            throw new RuntimeException("======== 존재하지 않는 회원입니다 ========");
        }

        return tokenProvider.createJwt(authentication);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserWithAuthorities(String userId) {
        return userRepository.findOneWithAuthoritiesByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUserId().flatMap(userRepository::findOneWithAuthoritiesByUserId);
    }
}
