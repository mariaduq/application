package com.example.demo.infraestructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.persistence.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNicknameOrEmail(String nickname, String email);

    Optional<UserEntity> findByEmail(String email);
}
