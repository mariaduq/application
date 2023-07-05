package com.example.demo.application.mapper;


import com.example.demo.application.UserInput;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component("applicationUserMapper")
@Service
public class UserMapper {

    public User fromInput(UserInput userInput) {
        return User.builder()
                .name(userInput.getName())
                .surname(userInput.getSurname())
                .nickname(userInput.getNickname())
                .email(userInput.getEmail())
                .password(userInput.getPassword())
                .confirmPassword(userInput.getConfirmPassword())
                .build();
    }
}