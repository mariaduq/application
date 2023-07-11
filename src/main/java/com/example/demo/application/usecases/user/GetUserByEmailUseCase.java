package com.example.demo.application.usecases.user;

import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UsersPort usersPort;

    public User execute(String email) throws Exception {
        return usersPort.getUserByEmail(email);
    }
}
