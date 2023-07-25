package com.example.demo.application.usecases.user;

import com.example.demo.application.input.UserInput;
import com.example.demo.infrastructure.persistence.adapter.UserAdapter;
import com.example.demo.infrastructure.persistence.entities.UserEntity;
import com.example.demo.infrastructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class UpdateUserUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userAdapter, new com.example.demo.application.mapper.UserMapper());

    @Test
    void should_update_user() throws Exception {
        //GIVEN
        String password = "password";
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        UserEntity user = buildUser(encodedPassword);
        UserInput userToUpdate = buildUserInput(password);

        when(userRepositoryJpa.findById(any()))
                .thenReturn(Optional.of(user));
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN
        updateUserUseCase.execute(userToUpdate);

        //THEN
        verify(userRepositoryJpa, times(1)).findById(any());
        verify(userRepositoryJpa, times(1)).save(user);
    }

    @Test
    void passwords_without_encode_does_not_match() throws Exception {
        //GIVEN
        String password = "password";
        UserEntity user = buildUser(password);
        UserInput userToUpdate = buildUserInput(password);

        when(userRepositoryJpa.findById(any()))
                .thenReturn(Optional.of(user));
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN; THEN
        assertThrows(Exception.class, ()-> updateUserUseCase.execute(buildUserInput("password")));
    }

    @Test
    void user_does_not_exist() {
        //GIVEN
        when(userRepositoryJpa.findById(any()))
                .thenReturn(Optional.empty());
        when(userRepositoryJpa.save(any()))
                .thenReturn(Optional.empty());

        //WHEN; THEN
        assertThrows(Exception.class, ()-> updateUserUseCase.execute(buildUserInput("password")));
    }

    private UserEntity buildUser(String password) {
        UserEntity user = new UserEntity();

        user.setId((long)1);
        user.setName("Maria");
        user.setSurname("Duque");
        user.setNickname("mdr10");
        user.setEmail("i92durom@uco.es");
        user.setPassword(password);
        user.setConfirmPassword(password);

        return user;
    }

    private UserInput buildUserInput(String password) {
        UserInput userInput = new UserInput();

        userInput.setId((long)1);
        userInput.setName("Maria");
        userInput.setSurname("Duque");
        userInput.setNickname("mdr10");
        userInput.setEmail("i92durom@uco.es");
        userInput.setPassword(password);
        userInput.setConfirmPassword(password);

        return userInput;
    }
}
