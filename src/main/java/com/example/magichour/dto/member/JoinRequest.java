package com.example.magichour.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class JoinRequest {
    @NotBlank(message = "이메일을 입력하세요")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요")
    private String userName;
}
