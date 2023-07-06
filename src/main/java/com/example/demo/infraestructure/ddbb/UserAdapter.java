package com.example.demo.infraestructure.ddbb;

import com.example.demo.application.UserInput;
import com.example.demo.application.ports.UsersPort;
import com.example.demo.infraestructure.ddbb.mappers.UserMapper;
import com.example.demo.infraestructure.ddbb.model.UserEntity;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;
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
            System.out.println(newUser.getName());
            System.out.println(newUser.getSurname());
            System.out.println(newUser.getEmail());
            System.out.println(newUser.getNickname());
            System.out.println(newUser.getPassword());
            System.out.println(newUser.getName());
            return userMapper.toDomain(userRepositoryJpa.save(userMapper.toEntity(newUser)));
        }
        return newUser;
    }

    @Override
    public User login(User userLogin) throws Exception {
        Optional<UserEntity> userFound = userRepositoryJpa.findByEmail(userLogin.getEmail());
        if(userFound.isPresent()){
            if(checkPassword(userMapper.toDomain(userFound.get()), userLogin.getPassword())) {
                return userMapper.toDomain(userFound.get());
            } else throw new Exception("Incorrect password. Try again.");
        } else throw new EntityNotFoundException("There is no user registered with this email. Try again.");
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

    private boolean checkPassword(User userFound, String loginPassword) {
        if(userFound.getPassword().equals(loginPassword)) return true;
        else return false;
    }
}
