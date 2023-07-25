package com.example.demo.application.usecases.user;

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

public class UpdateForgotPasswordUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    UpdateForgotPasswordUseCase updateForgotPasswordUseCase = new UpdateForgotPasswordUseCase(userAdapter, new com.example.demo.application.mapper.UserMapper());

    @Test
    void should_update_forgot_password() throws Exception {
        //GIVEN
        UserEntity user = buildUser();
        String password = "newPassword";

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.of(user));
        when(userRepositoryJpa.save(any()))
                .thenReturn(user);

        //WHEN
        updateForgotPasswordUseCase.execute(password, user.getEmail());

        //THEN
        verify(userRepositoryJpa, times(1)).findByEmail(any());
        verify(userRepositoryJpa, times(1)).save(user);
    }

    @Test
    void user_does_not_exist() {
        //GIVEN
        UserEntity user = buildUser();
        String password = "newPassword";

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.empty());
        when(userRepositoryJpa.save(any()))
                .thenReturn(Optional.empty());

        //WHEN; THEN
        assertThrows(Exception.class, ()-> updateForgotPasswordUseCase.execute(password, user.getEmail()));
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
}
