package com.example.magichour.service.member;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    Member join(JoinRequest joinRequest);
    TokenDto login(LoginRequest loginRequest, Authentication authentication);
    Optional<Member> getUserWithAuthorities(String userId);
    Optional<Member> getMyUserWithAuthorities();
}
