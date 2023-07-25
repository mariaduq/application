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

public class ChangePasswordUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    ChangePasswordUseCase changePasswordUseCase = new ChangePasswordUseCase(userAdapter);

    @Test
    void should_change_password_from_123_to_1234() throws Exception {
        //GIVEN
        String oldPassword = "123";
        String encodedPassword = bCryptPasswordEncoder.encode(oldPassword);
        String newPassword = "1234";
        String email = "i92durom@uco.es";

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.of(buildUser(encodedPassword)));
        when(userRepositoryJpa.save(any()))
                .thenReturn(buildUser(newPassword));

        //WHEN
        changePasswordUseCase.execute(email, oldPassword, newPassword, newPassword);

        //THEN
        verify(userRepositoryJpa, times(1)).findByEmail(any());
        verify(userRepositoryJpa, times(1)).save(any());
    }

    @Test
    void oldPassword_without_encode_not_equals_user_password() throws Exception {
        //GIVEN
        String oldPassword = "123";
        String newPassword = "1234";
        String email = "i92durom@uco.es";

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.of(buildUser(oldPassword)));
        when(userRepositoryJpa.save(any()))
                .thenReturn(buildUser(newPassword));

        //WHEN; THEN
        assertThrows(Exception.class, ()->changePasswordUseCase.execute(email, oldPassword, newPassword, newPassword));
    }

    @Test
    void newPassword_and_confirmNewPassword_not_match() throws Exception {
        //GIVEN
        String oldPassword = "123";
        String newPassword = "1234";
        String confirmNewPassword = "12345";
        String email = "i92durom@uco.es";

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.of(buildUser(oldPassword)));
        when(userRepositoryJpa.save(any()))
                .thenReturn(buildUser(newPassword));

        //WHEN; THEN
        assertThrows(Exception.class, ()->changePasswordUseCase.execute(email, oldPassword, newPassword, confirmNewPassword));
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
}
