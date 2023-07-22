package com.example.demo.application.usecases.user;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.port.UsersPort;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public UserOutput execute(UserInput userInput) throws Exception {
        System.out.println(userInput);
        return userMapper.toOutput(usersPort.update(userMapper.fromInput(userInput)));
    }
}
