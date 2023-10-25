package com.example.magichour;

import com.example.magichour.entity.Member;
import com.example.magichour.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void joinTest() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            Member member = Member.builder()
                    .userId("theanswerkk")
                    .userName("Kim Chanho")
                    .userPassword("rlacksgh3#")
                    .build();

            Member result = memberRepository.save(member);

            log.info("result = " + result.getId());
        });
    }
}
