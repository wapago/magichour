package com.example.magichour.controller.member;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;
import com.example.magichour.entity.member.UserEntity;
import com.example.magichour.service.member.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final AuthService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/join")
    public ResponseEntity<UserEntity> join(@Valid @RequestBody JoinRequest joinRequest) {
        log.info("======================= /member/join =======================");

        return ResponseEntity.ok(userService.join(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPassword());

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
    public ResponseEntity<UserEntity> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/banish/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserEntity> banish(@PathVariable("user_id") String userId) {
        log.info(userId + " 강퇴");
        return ResponseEntity.ok(userService.getUserWithAuthorities(userId).get());
    }
}
