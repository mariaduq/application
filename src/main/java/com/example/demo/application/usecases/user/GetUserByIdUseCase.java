package com.example.demo.application.usecases.user;

import com.example.demo.application.ports.UsersPort;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UsersPort usersPort;

    public User execute(Long id) {
        return usersPort.getUserById(id);
    }
}
