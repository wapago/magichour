package com.example.magichour.controller.member;

import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.JoinResponse;
import com.example.magichour.model.member.LoginRequest;
import com.example.magichour.model.member.LoginResponse;
import com.example.magichour.service.member.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    private static final String LOGIN_MEMBER = "loginMember";

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid JoinRequest joinRequest, HttpServletRequest request) throws Exception {
        userService.join(joinRequest);

        JoinResponse joinResponse = userService.joinAfter(joinRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/member/login"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = userService.login(loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
