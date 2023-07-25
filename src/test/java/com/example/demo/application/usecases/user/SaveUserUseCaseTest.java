package com.example.demo.application.usecases.user;

import com.example.demo.application.input.UserInput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.infrastructure.persistence.adapter.UserAdapter;
import com.example.demo.infrastructure.persistence.entities.UserEntity;
import com.example.demo.infrastructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveUserUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    SaveUserUseCase saveUserUseCase = new SaveUserUseCase(userAdapter, new com.example.demo.application.mapper.UserMapper());

    @Test
    void should_create_user() throws Exception {
        //GIVEN
        UserEntity user = buildUser();
        when(userRepositoryJpa.findByNicknameOrEmail(any(), any()))
                .thenReturn(Optional.empty());
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN
        UserOutput returnedUser = saveUserUseCase.execute(buildUserInput("password", "password"));

        //THEN
        verify(userRepositoryJpa, times(1)).save(any());
        verify(userRepositoryJpa, times(1)).findByNicknameOrEmail(any(), any());

        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(user.getName(), returnedUser.getName());
        assertEquals(user.getSurname(), returnedUser.getSurname());
        assertEquals(user.getNickname(), returnedUser.getNickname());
        assertEquals(user.getEmail(), returnedUser.getEmail());
        assertEquals(user.getPassword(), returnedUser.getPassword());
        assertEquals(user.getConfirmPassword(), returnedUser.getConfirmPassword());
    }

    @Test
    void password_and_confirmPassword_of_new_user_does_not_match() {
        //GIVEN
        UserEntity user = buildUser();
        when(userRepositoryJpa.findByNicknameOrEmail(any(), any()))
                .thenReturn(Optional.empty());
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN; THEN
        assertThrows(Exception.class, ()->saveUserUseCase.execute(buildUserInput("password1", "password2")));
    }

    @Test
    void new_user_email_or_new_user_nickname_are_not_available() {
        //GIVEN
        UserEntity user = buildUser();
        when(userRepositoryJpa.findByNicknameOrEmail(any(), any()))
                .thenReturn(Optional.of(user));
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN; THEN
        assertThrows(Exception.class, ()->saveUserUseCase.execute(buildUserInput("password", "password")));
    }

    private UserEntity buildUser() {
        UserEntity user = new UserEntity();

        user.setId((long)1);
        user.setName("Maria");
        user.setSurname("Duque");
        user.setNickname("mdr10");
        user.setEmail("i92durom@uco.es");
        user.setPassword("password");
        user.setConfirmPassword("password");

        return user;
    }

    private UserInput buildUserInput(String password, String confirmPassword) {
        UserInput userInput = new UserInput();

        userInput.setId((long)1);
        userInput.setName("Maria");
        userInput.setSurname("Duque");
        userInput.setNickname("mdr10");
        userInput.setEmail("i92durom@uco.es");
        userInput.setPassword(password);
        userInput.setConfirmPassword(confirmPassword);

        return userInput;
    }
}
