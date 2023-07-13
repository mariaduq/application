package com.example.demo.application.usecases.user;

import com.example.demo.application.ports.UsersPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UsersPort usersPort;

    public void execute(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        usersPort.changePassword(email, oldPassword, newPassword, confirmNewPassword);
    }
}
