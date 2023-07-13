package com.example.demo.application.usecases.user;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.port.UsersPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateForgotPasswordUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public void execute(String newPassword, String email) throws Exception {
        usersPort.updateForgotPassword(newPassword, email);
    }
}
