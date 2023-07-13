package com.example.demo.application.usecases.user;

import com.example.demo.domain.port.UsersPort;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UsersPort usersPort;

    public User execute(String email) throws Exception {
        return usersPort.getUserByEmail(email);
    }
}
