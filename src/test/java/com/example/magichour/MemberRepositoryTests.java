package com.example.magichour;

import com.example.magichour.entity.Member;
import com.example.magichour.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void joinTest() {
        IntStream.rangeClosed(2, 2).forEach(i -> {
            Member member = Member.builder()
                    .userId("thekk")
                    .userName("Kim Chanho")
                    .userPassword("rlacksgh3#")
                    .build();

            Member result = memberRepository.save(member);

        });
    }
}
