package com.example.demo.infraestructure.ddbb;

import com.example.demo.application.UserInput;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.infraestructure.ddbb.mappers.UserMapper;
import com.example.demo.infraestructure.ddbb.model.UserEntity;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAdapter implements UsersPort {

    private final UserRepositoryJpa userRepositoryJpa;

    private final UserMapper userMapper;

    @Override
    public User save(User newUser) throws Exception {
        if(checkNicknameAndEmailAvailable(newUser) && checkPasswordMatch(newUser)) {
            return userMapper.toDomain(userRepositoryJpa.save(userMapper.toEntity(newUser)));
        }
        return newUser;
    }

    private boolean checkNicknameAndEmailAvailable(User user) throws Exception {
        Optional<UserEntity> userFound = userRepositoryJpa.findByNicknameOrEmail(user.getNickname(), user.getEmail());
        if(userFound.isPresent()) {
            throw new Exception("Username or email already exists");
        }
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Passwords don't match");
        }
        return true;
    }
}
