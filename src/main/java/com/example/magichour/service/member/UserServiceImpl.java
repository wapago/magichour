package com.example.magichour.service.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.jwt.TokenProvider;
import com.example.magichour.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private MemberRepository memberRepository;
    private TokenProvider tokenProvider;

    public UserServiceImpl(MemberRepository repository, TokenProvider tokenProvider) {
        this.memberRepository = repository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Member join(JoinRequest joinRequest) {
        String requestId = joinRequest.getUserId();
        boolean isExistId = memberRepository.existsByUserId(requestId);

        if(isExistId) {
            throw new RuntimeException("======== 이미 존재하는 아이디입니다 ========");
        }

        Member member = Member.builder()
                .userId(joinRequest.getUserId())
                .userName(joinRequest.getUserName())
                .userPassword(joinRequest.getUserPassword())
                .build();

        memberRepository.save(member);

        return member;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getUserId());
        String userId = member.getUserId();

        return tokenProvider.createJwt(userId);
    }
}
