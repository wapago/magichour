package com.example.magichour.service.member;

import com.example.magichour.entity.member.Member;
import com.example.magichour.dto.member.JoinRequest;
import com.example.magichour.dto.member.LoginRequest;

public interface UserService {
    Member join(JoinRequest joinRequest);
    String login(LoginRequest loginRequest);
}
