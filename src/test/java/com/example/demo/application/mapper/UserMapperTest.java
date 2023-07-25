package com.example.demo.application.mapper;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper = new UserMapper();

    @Test
    void should_transform_from_User_to_UserOutput() {
        //GIVEN
        User user = User.builder()
                .id((long)1)
                .nickname("mdr10")
                .name("Maria")
                .surname("Duque")
                .email("i92durom@uco.es")
                .password("123")
                .confirmPassword("123")
                .build();

        //WHEN
        UserOutput userOutput = userMapper.toOutput(user);

        //THEN
        assertEquals(user.getId(), userOutput.getId());
        assertEquals(user.getNickname(), userOutput.getNickname());
        assertEquals(user.getName(), userOutput.getName());
        assertEquals(user.getSurname(), userOutput.getSurname());
        assertEquals(user.getEmail(), userOutput.getEmail());
        assertEquals(user.getPassword(), userOutput.getPassword());
        assertEquals(user.getConfirmPassword(), userOutput.getConfirmPassword());
    }

    @Test
    void should_transform_from_UserInput_to_User() {
        //GIVEN
        UserInput userInput = UserInput.builder()
                .id((long)1)
                .nickname("mdr10")
                .name("Maria")
                .surname("Duque")
                .email("i92durom@uco.es")
                .password("123")
                .confirmPassword("123")
                .build();

        //WHEN
        User user = userMapper.fromInput(userInput);

        //THEN
        assertEquals(userInput.getId(), user.getId());
        assertEquals(userInput.getNickname(), user.getNickname());
        assertEquals(userInput.getName(), user.getName());
        assertEquals(userInput.getSurname(), user.getSurname());
        assertEquals(userInput.getEmail(), user.getEmail());
        assertEquals(userInput.getPassword(), user.getPassword());
        assertEquals(userInput.getConfirmPassword(), user.getConfirmPassword());
    }
}
