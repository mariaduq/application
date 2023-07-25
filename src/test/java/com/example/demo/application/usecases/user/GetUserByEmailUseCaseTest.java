package com.example.demo.application.usecases.user;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetUserByEmailUseCaseTest {

    UserRepositoryJpa userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    UserAdapter userAdapter = new UserAdapter(userRepositoryJpa, new UserMapper(), bCryptPasswordEncoder);

    GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCase(userAdapter, new com.example.demo.application.mapper.UserMapper());

    @Test
    void should_return_user_by_email() throws Exception {
        //GIVEN
        UserEntity user = buildUser();
        String email = user.getEmail();

        when(userRepositoryJpa.findByEmail(any()))
                .thenReturn(Optional.of(user));

        //WHEN
        UserOutput returnedUser = getUserByEmailUseCase.execute(email);

        //THEN
        verify(userRepositoryJpa, times(1)).findByEmail(any());

        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(user.getName(), returnedUser.getName());
        assertEquals(user.getSurname(), returnedUser.getSurname());
        assertEquals(user.getNickname(), returnedUser.getNickname());
        assertEquals(user.getEmail(), returnedUser.getEmail());
        assertEquals(user.getPassword(), returnedUser.getPassword());
        assertEquals(user.getConfirmPassword(), returnedUser.getConfirmPassword());
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
