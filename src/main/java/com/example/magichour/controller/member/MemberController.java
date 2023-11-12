package com.example.magichour.controller.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.LoginRequest;
import com.example.magichour.service.member.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Log4j2
public class MemberController {
    private static final String LOGIN_MEMBER = "loginMember";

    private UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public void join(@Valid JoinRequest joinRequest) {
        userService.join(joinRequest);
    }

    @PostMapping("/login")
    public void login(@Valid LoginRequest loginRequest, HttpSession httpSession) throws Exception {
        Member member = userService.login(loginRequest);
        httpSession.setAttribute(LOGIN_MEMBER, member);
    }

}
