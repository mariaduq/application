package com.example.demo.application.usecases.user;

import com.example.demo.infrastructure.persistence.adapter.UserAdapter;
import com.example.demo.infrastructure.persistence.entities.UserEntity;
import com.example.demo.infrastructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteUserUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userAdapter);

    @Test
    void should_delete_user() throws Exception {
        //GIVEN
        UserEntity user = buildUser();

        when(userRepositoryJpa.findById(any()))
                .thenReturn(Optional.of(user));
        doNothing().when(userRepositoryJpa).delete(user);

        //WHEN
        deleteUserUseCase.execute(user.getId());

        //THEN
        verify(userRepositoryJpa, times(1)).findById(any());
        verify(userRepositoryJpa, times(1)).delete(user);
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
