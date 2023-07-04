package com.example.demo.infraestructure.ddbb;

import com.example.demo.application.ports.UsersPort;
import com.example.demo.infraestructure.ddbb.mappers.UserMapper;
import com.example.demo.model.User;

public class UserAdapter implements UsersPort {

    private UserRepositoryJpa userRepositoryJpa;

    private UserMapper userMapper;

    public UserAdapter(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public User save(User userRegistry) {
        return userMapper.toDomain(userRepositoryJpa.save(userMapper.toEntity(userRegistry)));
    }
}
