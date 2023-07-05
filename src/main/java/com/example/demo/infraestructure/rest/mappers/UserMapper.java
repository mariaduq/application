package com.example.demo.infraestructure.rest.mappers;

import com.example.demo.application.UserInput;
import com.example.demo.infraestructure.rest.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("apiUserMapper")
public class UserMapper {
    public UserInput toUserInput(UserDTO userDTO) {
        return UserInput.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .confirmPassword(userDTO.getConfirmPassword())
                .build();
    }
}

