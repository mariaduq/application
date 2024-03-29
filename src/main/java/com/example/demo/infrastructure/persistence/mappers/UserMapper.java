package com.example.demo.infrastructure.persistence.mappers;

import com.example.demo.infrastructure.persistence.entities.UserEntity;
import com.example.demo.domain.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@NoArgsConstructor
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
                .confirmPassword(userEntity.getConfirmPassword())
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
                .confirmPassword(user.getConfirmPassword())
                .build();
    }

}
