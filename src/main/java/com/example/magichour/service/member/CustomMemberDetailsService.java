package com.example.magichour.service.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component("memberDetailsService")
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomMemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) {
        return memberRepository.findOneWithAuthoritiesByUserId(userId)
                .map(member -> createUser(userId, member))
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userId, Member member) {
        if(!member.isActivated()) {
            throw new RuntimeException(userId + "는 활성화되어있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority().name()));

        for(GrantedAuthority authority : grantedAuthorities) {
            log.info(authority.toString());
        }

        return new org.springframework.security.core.userdetails.User(member.getUserId(), member.getUserPassword(), grantedAuthorities);
    }
}
