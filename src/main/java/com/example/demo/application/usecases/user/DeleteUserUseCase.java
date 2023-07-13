package com.example.demo.application.usecases.user;

import com.example.demo.domain.port.UsersPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UsersPort usersPort;

    public void execute(Long id) throws Exception {
        usersPort.deleteUser(id);
    }
}
