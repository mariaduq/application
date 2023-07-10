package com.example.demo.application;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public void execute(String newPassword, String email) throws Exception {
        usersPort.updatePassword(newPassword, email);
    }
}
