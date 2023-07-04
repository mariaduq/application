package com.example.demo.infraestructure.ddbb.mappers;

import com.example.demo.infraestructure.ddbb.model.UserEntity;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public User toDomain(UserEntity userEntity) {
        if(Objects.isNull(userEntity)) {
            return null;
        }

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .password(userEntity.getPassword())
                .build();
    }

    public UserEntity toEntity(User user) {
        if(Objects.isNull(user)) {
            return null;
        }

        return UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }

}
