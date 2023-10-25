package com.example.magichour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MagichourApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagichourApplication.class, args);
    }

}
