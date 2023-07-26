package com.example.demo.application.usecases.user;

import com.example.demo.domain.port.UsersPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateForgotPasswordUseCase {

    private final UsersPort usersPort;

    public void execute(String newPassword, String email) throws Exception {
        usersPort.updateForgotPassword(newPassword, email);
    }
}
