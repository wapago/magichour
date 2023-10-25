package com.example.magichour.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/join")
    public String joinPage() {
        log.info("join get.............");

        return "/member/join";
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("login get......");

        return "/member/login";
    }
}

