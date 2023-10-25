package com.example.magichour.service.member;

import com.example.magichour.model.member.JoinRequest;
import com.example.magichour.model.member.JoinResponse;
import com.example.magichour.model.member.LoginRequest;
import com.example.magichour.model.member.LoginResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    @Override
    public void join(JoinRequest joinRequest) {

    }

    @Override
    public JoinResponse joinAfter(JoinRequest joinRequest) {

        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
