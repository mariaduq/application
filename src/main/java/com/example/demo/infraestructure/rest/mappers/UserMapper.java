package com.example.demo.infraestructure.rest.mappers;

import com.example.demo.application.UserInput;
import com.example.demo.infraestructure.rest.UserDTO;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("apiUserMapper")
public class UserMapper {
    public UserInput toUserInput(UserDTO userDTO) {
        return UserInput.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .confirmPassword(userDTO.getConfirmPassword())
                .build();
    }

    public UserDTO fromUserToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .confirmPassword(user.getConfirmPassword())
                .build();
    }
}

