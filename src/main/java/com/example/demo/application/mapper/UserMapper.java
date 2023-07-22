package com.example.demo.application.mapper;


import com.example.demo.application.input.UserInput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component("applicationUserMapper")
@Service
public class UserMapper {

    public User fromInput(UserInput userInput) {
        return User.builder()
                .id(userInput.getId())
                .name(userInput.getName())
                .surname(userInput.getSurname())
                .nickname(userInput.getNickname())
                .email(userInput.getEmail())
                .password(userInput.getPassword())
                .confirmPassword(userInput.getConfirmPassword())
                .build();
    }

    public UserOutput toOutput(User user) {
        return UserOutput.builder()
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