package com.example.demo.application;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public User execute(UserInput userInput) throws Exception {
        return usersPort.login(userMapper.fromInput(userInput));
    }
}
