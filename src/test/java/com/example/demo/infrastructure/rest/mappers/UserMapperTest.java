package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.model.User;
import com.example.demo.infrastructure.rest.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void should_transform_from_dto_to_input() {
        //GIVEN
        UserDTO userDTO = UserDTO.builder()
                .id((long)4)
                .nickname("jesusv")
                .name("Jesus")
                .surname("Ventura")
                .email("jv98@uco.es")
                .password("jv98")
                .confirmPassword("jv98")
                .build();

        //WHEN
        UserInput userInput = userMapper.toUserInput(userDTO);

        //THEN
        assertEquals(userDTO.getId(), userInput.getId());
        assertEquals(userDTO.getNickname(), userInput.getNickname());
        assertEquals(userDTO.getName(), userInput.getName());
        assertEquals(userDTO.getSurname(), userInput.getSurname());
        assertEquals(userDTO.getEmail(), userInput.getEmail());
        assertEquals(userDTO.getPassword(), userInput.getPassword());
        assertEquals(userDTO.getConfirmPassword(), userInput.getConfirmPassword());
    }

    @Test
    void should_transform_from_domain_to_dto() {
        //GIVEN
        User user = User.builder()
                .id((long)5)
                .nickname("juanh")
                .name("Juan")
                .surname("Huertas")
                .email("juan@uco.es")
                .password("j96")
                .confirmPassword("j96")
                .build();

        //WHEN
        UserDTO userDTO = userMapper.fromUserToUserDTO(user);

        //THEN
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getNickname(), userDTO.getNickname());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getSurname(), userDTO.getSurname());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getConfirmPassword(), userDTO.getConfirmPassword());
    }

    @Test
    void should_transform_from_output_to_dto() {
        //GIVEN
        UserOutput userOutput = UserOutput.builder()
                .id((long)6)
                .nickname("miguelh")
                .name("Miguel")
                .surname("Huertas")
                .email("miguel@uco.es")
                .password("m98")
                .confirmPassword("m98")
                .build();

        //WHEN
        UserDTO userDTO = userMapper.fromUserOutputToUserDTO(userOutput);

        //THEN
        assertEquals(userOutput.getId(), userDTO.getId());
        assertEquals(userOutput.getNickname(), userDTO.getNickname());
        assertEquals(userOutput.getName(), userDTO.getName());
        assertEquals(userOutput.getSurname(), userDTO.getSurname());
        assertEquals(userOutput.getEmail(), userDTO.getEmail());
        assertEquals(userOutput.getPassword(), userDTO.getPassword());
        assertEquals(userOutput.getConfirmPassword(), userDTO.getConfirmPassword());
    }
}
