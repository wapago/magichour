package com.example.magichour.service.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.LoginRequest;

public interface UserService {
    void join(JoinRequest joinRequest);
    Member login(LoginRequest loginRequest);
}
