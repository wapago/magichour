package com.example.magichour.service.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    private MemberRepository memberRepository;

    public UserServiceImpl(MemberRepository repository) {
        this.memberRepository = repository;
    }

    @Override
    public void join(JoinRequest joinRequest) {
        Member member = Member.builder()
                .userId(joinRequest.getUserId())
                .userName(joinRequest.getUserName())
                .userPassword(joinRequest.getUserPassword())
                .build();

        memberRepository.save(member);
    }

    @Override
    public Member login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getUserId());

        return member;
    }
}
