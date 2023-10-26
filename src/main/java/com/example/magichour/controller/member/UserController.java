package com.example.magichour.controller.member;

import com.example.magichour.entity.Member;
import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.LoginRequest;
import com.example.magichour.service.member.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    private static final String LOGIN_MEMBER = "loginMember";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public void join(@Valid JoinRequest joinRequest, HttpServletResponse response) throws Exception {
        userService.join(joinRequest);

        response.sendRedirect("/member/login");
    }

    @PostMapping("/login")
    public void login(@Valid LoginRequest loginRequest, HttpSession httpSession, HttpServletResponse response) throws Exception {
        Member member = userService.login(loginRequest);
        httpSession.setAttribute(LOGIN_MEMBER, member);

        response.sendRedirect("/");
    }

}
