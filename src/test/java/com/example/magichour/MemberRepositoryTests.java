package com.example.magichour;

import com.example.magichour.entity.member.UserEntity;
import com.example.magichour.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private UserRepository memberRepository;

    @Test
    public void joinTest() {
        IntStream.rangeClosed(2, 2).forEach(i -> {
            UserEntity member = UserEntity.builder()
                    .userEmail("thekk")
                    .userName("Kim Chanho")
                    .userPassword("rlacksgh3#")
                    .build();

            UserEntity result = memberRepository.save(member);

        });
    }
}
