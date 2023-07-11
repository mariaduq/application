package com.example.demo.application;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UsersPort usersPort;

    public void execute(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        usersPort.changePassword(email, oldPassword, newPassword, confirmNewPassword);
    }
}
