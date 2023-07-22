package com.example.demo.application.usecases.user;

import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.port.UsersPort;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UsersPort usersPort;

    private final UserMapper userMapper;

    public UserOutput execute(Long id) {
        return userMapper.toOutput(usersPort.getUserById(id));
    }
}
