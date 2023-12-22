package com.example.magichour.controller.member;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.service.member.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@Log4j2
public class MemberController {
    private UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public MemberController(UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody JoinRequest joinRequest) {
        return ResponseEntity.ok(userService.join(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getUserPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = userService.login(loginRequest, authentication);
        String accessToken = tokenDto.getAccessToken();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + accessToken);

        return new ResponseEntity<>(tokenDto, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getMyUserInfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/banish/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> banish(@PathVariable("user_id") String userId) {
        log.info(userId + " 강퇴");
        return ResponseEntity.ok(userService.getUserWithAuthorities(userId).get());
    }
}
