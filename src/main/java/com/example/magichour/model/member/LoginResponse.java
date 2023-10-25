package com.example.magichour.model.member;

import lombok.Data;

@Data
public class LoginResponse {
    private String userId;
    private String userPassword;
    private String userName;
}
