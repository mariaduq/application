package com.example.demo.application.usecases.user;

import com.example.demo.domain.port.UsersPort;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UsersPort usersPort;

    public User execute(Long id) {
        return usersPort.getUserById(id);
    }
}
