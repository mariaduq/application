package com.example.demo.application.usecases.user;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.port.UsersPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public UserOutput execute(UserInput userInput) throws Exception {
        return userMapper.toOutput(usersPort.update(userMapper.fromInput(userInput)));
    }
}
