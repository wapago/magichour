package com.example.magichour.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {
    @NotBlank(message = "아이디를 입력하세요")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPassword;
}
