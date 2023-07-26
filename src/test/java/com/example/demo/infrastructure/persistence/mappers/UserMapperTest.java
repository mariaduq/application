package com.example.demo.infrastructure.persistence.mappers;

import com.example.demo.domain.model.User;
import com.example.demo.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void should_transform_to_domain() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .id((long)2)
                .nickname("mduq01")
                .name("Maria")
                .surname("Duque")
                .email("mariaduq01@uco.es")
                .password("1234")
                .confirmPassword("1234")
                .build();

        //WHEN
        User user = userMapper.toDomain(userEntity);

        //THEN
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getNickname(), user.getNickname());
        assertEquals(userEntity.getName(), user.getName());
        assertEquals(userEntity.getSurname(), user.getSurname());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertEquals(userEntity.getConfirmPassword(), user.getConfirmPassword());
    }

    @Test
    void should_transform_to_entity() {
        //GIVEN
        User user = User.builder()
                .id((long)3)
                .nickname("mdrz10")
                .name("Natalia")
                .surname("Roman")
                .email("natalia@uco.es")
                .password("abcd")
                .confirmPassword("abcd")
                .build();

        //WHEN
        UserEntity userEntity = userMapper.toEntity(user);

        //THEN
        assertEquals(user.getNickname(), userEntity.getNickname());
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getSurname(), userEntity.getSurname());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getConfirmPassword(), userEntity.getConfirmPassword());
    }
}
