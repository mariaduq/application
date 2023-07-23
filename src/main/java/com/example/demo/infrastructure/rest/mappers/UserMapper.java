package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.infrastructure.rest.dto.UserDTO;
import com.example.demo.domain.model.User;
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

    public UserDTO fromUserOutputToUserDTO(UserOutput userOutput) {
        return UserDTO.builder()
                .id(userOutput.getId())
                .name(userOutput.getName())
                .surname(userOutput.getSurname())
                .nickname(userOutput.getNickname())
                .email(userOutput.getEmail())
                .password(userOutput.getPassword())
                .confirmPassword(userOutput.getConfirmPassword())
                .build();
    }
}

