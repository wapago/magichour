package com.example.magichour.service.member;

import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.JoinResponse;
import com.example.magichour.model.member.LoginRequest;
import com.example.magichour.model.member.LoginResponse;

public interface UserService {
    void join(JoinRequest joinRequest);
    JoinResponse joinAfter(JoinRequest joinRequest);
    LoginResponse login(LoginRequest loginRequest);
}
