package com.example.magichour.service.member;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.entity.member.Authority;
import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.jwt.TokenProvider;
import com.example.magichour.repository.MemberRepository;
import com.example.magichour.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;


@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private MemberRepository memberRepository;
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(MemberRepository memberRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member join(JoinRequest joinRequest) {
        String requestId = joinRequest.getUserId();
        boolean isExistId = memberRepository.existsByUserId(requestId);

        if (isExistId) {
            throw new RuntimeException("======== 이미 존재하는 아이디입니다 ========");
        }

        Authority authority = Authority.USER;

        Member member = Member.builder()
                .userId(joinRequest.getUserId())
                .userName(joinRequest.getUserName())
                .userPassword(passwordEncoder.encode(joinRequest.getUserPassword()))
                .authority(authority)
                .activated(true)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Override
    public TokenDto login(LoginRequest loginRequest, Authentication authentication) {
        Optional<Member> memberOptional = memberRepository.findByUserId(loginRequest.getUserId());

        if (!memberOptional.isPresent()) {
            throw new RuntimeException("======== 존재하지 않는 회원입니다 ========");
        }

        return tokenProvider.createJwt(authentication);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String userId) {
        return memberRepository.findOneWithAuthoritiesByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUserId().flatMap(memberRepository::findOneWithAuthoritiesByUserId);
    }
}
