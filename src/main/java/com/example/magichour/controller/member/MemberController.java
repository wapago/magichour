package com.example.magichour.controller.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.service.member.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/member")
@Log4j2
public class MemberController {
    private UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody JoinRequest joinRequest) {
        return ResponseEntity.ok(userService.join(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        String token = userService.login(loginRequest);

        return ResponseEntity.ok(token);
    }

}
