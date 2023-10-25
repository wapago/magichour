package com.example.magichour.model.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JoinRequest {
    @NotBlank(message = "아이디를 입력하세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요")
    private String userName;
}
