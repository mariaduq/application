package com.example.demo.application;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public User execute(UserInput userInput) throws Exception {
        System.out.println(userInput);
        return usersPort.update(userMapper.fromInput(userInput));
    }
}